(load "pmatch.scm")
(load "test.scm")

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
    (pmatch k
      [`(empty-k-pascal-tramp ,j) (j v)]
      [`(else-k-inner-pascal-tramp ,a ,k) (app-k-pascal-tramp k (cons a v))]
      [`(else-k-outer-pascal-tramp ,m ,a ,k) (v (add1 m) a (else-k-inner-pascal-tramp a k))]
      [`(arg-k-pascal-tramp ,k) (v 1 0 k)])))
  

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
    (let ((pascal
           (lambda (pascal k)
             (app-k-pascal-tramp k (lambda (m a k)
      (cond
        [(> m n) (app-k-pascal-tramp k '())]
        [else (let ((a (+ a m)))
              (pascal pascal (else-k-outer-pascal-tramp m a k)))]))))))
      (pascal pascal (arg-k-pascal-tramp k)))))

(define pascal-tramp-driver
  (lambda (n)
    (call/cc
      (lambda (j)
        (trampoline-pascal (pascal n (empty-k-pascal-tramp j)))))))

(define trampoline-pascal
  (lambda (th)
    (trampoline-pascal (th))))

