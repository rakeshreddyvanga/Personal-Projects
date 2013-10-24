(load "pmatch.scm")
(load "test.scm")

(define empty-k-ack
  (lambda ()
      `(empty-k-ack)))

(define else-k-ack
  (lambda (m k)
   `(else-k-ack ,m ,k)))

(define app-k-ack
 (lambda () ;; *k *v
  (pmatch *k
   [`(empty-k-ack) *v]
   [`(else-k-ack ,m ,k)
      (begin 
        (set! *k k)
        (set! *n *v)
        (set! *m (sub1 m))
        (ack-reg))])))

(define ack-reg
  (lambda () ;; *m *n *k
    (cond
      [(zero? *m) 
        (begin
          (set! *k *k)
          (set! *v (add1 *n))
          (app-k-ack))]
      [(zero? *n)
        (begin 
          (set! *k *k)
          (set! *n 1)
          (set! *m (sub1 *m))
          (ack-reg))]
      [else
        (begin
          (set! *k (else-k-ack *m *k))
          (set! *n (sub1 *n))
          (set! *m *m)
          (ack-reg))])))

(define ack-reg-driver
  (lambda (m n)
   (begin 
     (set! *k (empty-k-ack))
         (set! *m m)
         (set! *n n)
         (ack-reg))))


(define ack
  (lambda (m n k)
    (cond
      [(zero? m) (k (add1 n))]
      [(zero? n) (ack (sub1 m) 1 k)]
      [else (ack m (sub1 n) (lambda (v) (ack (sub1 m) v k)))])))


(define empty-k-depth
  (lambda ()
   `(empty-k-depth)))

(define app-k-depth
  (lambda () ;; *k *v
    (pmatch *k
      [`(empty-k-depth) *v]
      [`(pair-inner-depth ,l ,k)  (begin
                                  (set! l (add1 l))
                                  (set! *k k)
                                  (set! *v (if (< l *v) *v l))
                                  (app-k-depth))]
      [`(pair-outer-depth ,ls ,k) 
          (begin
            (set! *k  (pair-inner-depth *v k))
            (set! *ls (cdr ls))
            (depth-reg))])))
   

(define pair-inner-depth
  (lambda (l k)
    `(pair-inner-depth ,l ,k)))

(define pair-outer-depth
  (lambda (ls k)
    `(pair-outer-depth ,ls ,k)))

(define depth-reg-driver
  (lambda (ls)
     (begin 
      (set! *k (empty-k-depth))
      (set! *ls ls)
      (depth-reg))))

(define depth-reg
  (lambda () ;; *ls *k
    (cond
      [(null? *ls) 
        (begin
          (set! *k *k)
          (set! *v 1)
          (app-k-depth))]
      [(pair? (car *ls))
        (begin
          (set! *k (pair-outer-depth *ls *k))
          (set! *ls (car *ls))
          (depth-reg))]
      [else
        (begin
          (set! *k *k)
          (set! *ls (cdr *ls))
          (depth-reg))])))


(define empty-k-fact
  (lambda ()
    `(empty-k-fact)))

(define app-k-fact
  (lambda () ;; *k *v
    (pmatch-who "appk" *k
      [`(empty-k-fact) *v]
      [`(else-k-fact ,n ,k) 
          (begin
          (set! *k k)
          (set! *v (* n *v))
          (app-k-fact))])))

(define apply-closure-fact
  (lambda () ;; *clos *a *k
    (pmatch-who "closures" *clos
      [`(outer-lambda-fact ,n) 
        (begin
        (set! *k *k)
        (set! *a n)
        (set! *clos (inner-lambda-fact))
        (apply-closure-fact))]
      [`(inner-lambda-fact)  (cond
                                [(zero? *a) 
                                   (begin
                                   (set! *k *k)
                                   (set! *v 1)
                                   (app-k-fact))]
                                [else
                                    (begin
                                    (set! *k (else-k-fact *a *k))
                                    (set! *a (sub1 *a))
                                    (set! *clos (inner-lambda-fact))
                                    (apply-closure-fact))])])))


(define outer-lambda-fact
  (lambda (n)
    `(outer-lambda-fact ,n)))    

(define inner-lambda-fact
  (lambda ()
  `(inner-lambda-fact)))
   
(define else-k-fact
  (lambda (n k)
   `(else-k-fact ,n ,k)))

(define fact-reg
  (lambda () ;; *n *k
    (begin
      (set! *clos (outer-lambda-fact *n))
      (set! *a (inner-lambda-fact))
      (set! *k *k)
      (apply-closure-fact))))

(define fact-reg-driver 
  (lambda (n)
    (begin
      (set! *k (empty-k-fact))
      (set! *n n)
      (fact-reg))))

(define fact
  (lambda (n k)
    ((trace-lambda func (fact k)`
       (fact fact n k))
     (trace-lambda aug (fact n k)
       (cond
         [(zero? n) (k 1)]
         [else (fact fact (sub1 n) (lambda (v) (k (* n v))))]))
     k)))

(define empty-k-ack-tramp
  (lambda (j)
      `(empty-k-ack-tramp ,j)))

(define else-k-ack-tramp
  (lambda (m k)
   `(else-k-ack-tramp ,m ,k)))

(define app-k-ack-tramp
 (lambda (k v)
  (lambda ()
   (pmatch k
    [`(empty-k-ack-tramp ,j) (j v)]
    [`(else-k-ack-tramp ,m ,k) (ack-reg-tramp (sub1 m) v k)]))))

(define ack-reg-tramp
  (lambda (m n k)
   (lambda ()
    (cond
      [(zero? m) (app-k-ack-tramp k (add1 n))]
      [(zero? n) (ack-reg-tramp (sub1 m) 1 k)]
      [else (ack-reg-tramp m (sub1 n) (else-k-ack-tramp m k))]))))

(define ack-tramp-driver
  (lambda (m n)
   (call/cc
    (lambda (j)
      (trampoline-ack (ack-reg-tramp m n (empty-k-ack-tramp j)))))))

(define trampoline-ack
  (lambda (th)
    (trampoline-ack (th))))


(define empty-k-depth-tramp
  (lambda (j)
   `(empty-k-depth-tramp ,j)))

(define app-k-depth-tramp
  (lambda (k v)
    (lambda ()
    (pmatch k
      [`(empty-k-depth-tramp ,j) (j v)]
      [`(pair-inner-depth-tramp ,l ,k) (let ((l (add1 l)))
                                             (app-k-depth-tramp k (if (< l v) v l)))]
      [`(pair-outer-depth-tramp ,ls ,k) 
                                      (depth-reg-tramp (cdr ls) (pair-inner-depth-tramp v k))]))))
   

(define pair-inner-depth-tramp
  (lambda (l k)
    `(pair-inner-depth-tramp ,l ,k)))

(define pair-outer-depth-tramp
  (lambda (ls k)
    `(pair-outer-depth-tramp ,ls ,k)))

(define depth-tramp-driver
  (lambda (ls)
    (call/cc 
      (lambda (j)
        (trampoline-depth (depth-reg-tramp ls (empty-k-depth-tramp j)))))))

(define trampoline-depth
  (lambda (th)
    (trampoline-depth (th))))

(define depth-reg-tramp
  (lambda (ls k)
    (lambda ()
    (cond
      [(null? ls) (app-k-depth-tramp k 1)]
      [(pair? (car ls)) (depth-reg-tramp (car ls) (pair-outer-depth-tramp ls k))]
      [else (depth-reg-tramp (cdr ls) k)]))))

(define else-k-fact-tramp
  (lambda (n k)
   `(else-k-fact-tramp ,n ,k)))

(define fact-reg-tramp
  (lambda (n k) 
    (lambda ()
   ((lambda (fact-reg-tramp k)
      (fact-reg-tramp fact-reg-tramp n k))
    (lambda (fact-reg-tramp n k)
      (cond
        [(zero? n) (app-k-fact-tramp k 1)]
        [else (fact-reg-tramp fact-reg-tramp (sub1 n) (else-k-fact-tramp n k))]))
    k))))

(define fact-tramp-driver
  (lambda (n)
    (call/cc
      (lambda (j)
        (trampoline-fact (fact-reg-tramp n (empty-k-fact-tramp j)))))))

(define trampoline-fact
  (lambda (th)
    (trampoline-fact (th))))

(define empty-k-fact-tramp
  (lambda (j)
    `(empty-k-fact-tramp ,j)))

(define app-k-fact-tramp
  (lambda (k v)
    (lambda ()
    (pmatch-who "appk" k
      [`(empty-k-fact-tramp ,j) (j v)]
      [`(else-k-fact-tramp ,n ,k) (app-k-fact-tramp k (* n v))]))))

(define empty-k-pascal-tramp
  (lambda (j)
    `(empty-k-pascal-tramp ,j)))

(define app-k-pascal-tramp
  (lambda (k v)
    (lambda ()
    (pmatch k
      [`(empty-k-pascal-tramp ,j) (j v)]
      [`(else-k-inner-pascal-tramp ,a ,k) (app-k-pascal-tramp k (cons a v))]
      [`(else-k-outer-pascal-tramp ,m ,a ,k) (v (add1 m) a (else-k-inner-pascal-tramp a k))]
      [`(arg-k-pascal-tramp ,k) (v 1 0 k)]))))
  

(define else-k-inner-pascal-tramp
  (lambda (a k)
    `(else-k-inner-pascal-tramp ,a ,k)))

(define else-k-outer-pascal-tramp
  (lambda (m a k)
    `(else-k-outer-pascal-tramp ,m ,a ,k)))

(define arg-k-pascal-tramp
  (lambda (k)
     `(arg-k-pascal-tramp ,k)))

(define pascal
  (lambda (n k)
    (lambda ()
    (let ((pascal
           (lambda (pascal k)
             (app-k-pascal-tramp k (lambda (m a k)
      (cond
        [(> m n) (app-k-pascal-tramp k '())]
        [else (let ((a (+ a m)))
              (pascal pascal (else-k-outer-pascal-tramp m a k)))]))))))
      (pascal pascal (arg-k-pascal-tramp k))))))

(define pascal-tramp-driver
  (lambda (n)
    (call/cc
      (lambda (j)
        (trampoline-pascal (pascal n (empty-k-pascal-tramp j)))))))

(define trampoline-pascal
  (lambda (th)
    (trampoline-pascal (th))))


(define empty-k-pascal
  (lambda ()
    `(empty-k-pascal)))

(define app-k-pascal
  (lambda () ;; *k *v
    (pmatch *k
      [`(empty-k-pascal) *v]
      [`(else-k-inner-pascal ,a ,k)
        (begin
        (set! *k k)
        (set! *v (cons a *v))
        (app-k-pascal))]
      [`(else-k-outer-pascal ,m ,a ,k)
        (begin
          (set! *k (else-k-inner-pascal a k))
          (set! *a a)
          (set! *m (add1 m))
          (*v))]
      [`(arg-k-pascal ,k)
        (begin
          (set! *k k)
          (set! *a 0)
          (set! *m 1)
          (*v))])))
  

(define else-k-inner-pascal
  (lambda (a k)
    `(else-k-inner-pascal ,a ,k)))

(define else-k-outer-pascal
  (lambda (m a k)
    `(else-k-outer-pascal ,m ,a ,k)))

(define arg-k-pascal
  (lambda (k)
     `(arg-k-pascal ,k)))

(define pascal-reg
  (lambda () ;; *n *k
    (begin 
    (set! *pascal  (lambda () ;; *pascal *k
                             (begin 
                               (set! *k *k)
                               (set! *v (lambda () ;; *m *a *k
                                   (cond
                                     [(> *m *n)
                                        (begin
                                        (set! *k *k)
                                        (set! *v '())
                                        (app-k-pascal))]
                                     [else
                                      (begin 
                                        (set! *a (+ *a *m))
                                        (set! *k (else-k-outer-pascal *m *a *k))
                                        (set! *pascal *pascal)
                                        (*pascal))])))
                (app-k-pascal))))
      (set! *k (arg-k-pascal *k))
      (set! *pascal *pascal)
      (*pascal))))

(define pascal-reg-driver
  (lambda (n)
    (begin
      (set! *k (empty-k-pascal))
      (set! *n n)
      (pascal-reg))))

(define fib-cps
  (lambda (n)
    (cond
      [(= n 0) 1]
      [(= n 1) 1]
      [else (+ (fib-cps (sub1 n)) (fib-cps (sub1 (sub1 n))))])))


(define empty-k-fib
  (lambda (j)
    `(empty-k-fib ,j)))

(define app-k-fib 
 (lambda (k v)
  (lambda ()
  (pmatch k
    [`(empty-k-fib ,j) (j v)]
    [`(inner-fib-k ,w ,k) (app-k-fib k (+ w v))]
    [`(outer-fib-k ,n ,k) (fib (sub1 (sub1 n)) (inner-fib-k v k))]))))
  

(define inner-fib-k
  (lambda (w k)
    `(inner-fib-k ,w ,k)))
    
  
 (define outer-fib-k
  (lambda (n k)
    `(outer-fib-k ,n ,k))) 

(define fib
  (lambda (n k)
    (lambda ()
    (cond
      [(= n 0) (app-k-fib k 1)]
      [(= n 1) (app-k-fib k 1)]
      [else (fib (sub1 n) (outer-fib-k n k))]))))

(define rampoline
  (lambda (n1 n2 n3)
    (rampoline n2 n3 (n1))))


(define fib-dr
  (lambda (n1 n2 n3)
    (call/cc
      (lambda (jumpout)
  (rampoline
      (lambda ()
        (fib n1 (empty-k-fib jumpout)))
      (lambda ()
        (fib n2 (empty-k-fib (empty-k-fib))))
            (lambda ()
        (fib n3 (empty-k-fib jumpout))))))))
