(load "pmatch.scm")
(load "test.scm")
;;(load "smart-lambda.ss")


(define empty-k
  (lambda ()
    (let ((once-only #f))
      (lambda (v)
        (if once-only 
          (error 'empty-k "You can only invoke the empty continuation once")
          (begin (set! once-only #t) v))))))

(define fact
  (trace-lambda factmain (n)
      (cond
            [(zero? n) 1]
                  [else (* n (fact (sub1 n)))])))

(define fact-cps
  (trace-lambda factcpsmain (n k)
      (cond
            [(zero? n) (k 1)]
                  [else (fact-cps (sub1 n) (lambda (v)
                                                   (k (* n v))))])))

(define times
  (trace-lambda timesmain (ls)
    (cond
      [(null? ls) 1]
      [(zero? (car ls)) 0]
      [else (* (car ls) (times (cdr ls)))])))

(define times-cps 
  (trace-lambda timestracecps  (ls k)
    (cond
      [(null? ls) (k 1)]
      [(zero? (car ls)) (k 0)]
      [else (times-cps (cdr ls) (lambda (v) 
                                  (k (* (car ls) v))))])))
(define times-cps-shortcut 
  (lambda (ls k)
    (cond
      [(null? ls) (k 1)]
      [(zero? (car ls)) 0]
      [else (times-cps-shortcut (cdr ls) (trace-lambda inner (v) 
                                  (k (* (car ls) v))))])))

    
(trace-define plus-cps  
  (lambda (x k)
    (k (lambda (y k)
     (k (+ x y))))))

(define count-syms*-cps
  (lambda (ls k)
    (cond
      [(null? ls) (k 0)]
      [(pair? (car ls)) (count-syms*-cps (car ls) (lambda (v) (count-syms*-cps (cdr ls) (lambda (w) (k (+ v w))))))]
      [(symbol? (car ls)) (count-syms*-cps (cdr ls) (lambda (v) (k (add1 v))))]
      [else (count-syms*-cps (cdr ls) (lambda (v) (k v)))])))


(define cons-cell-count-cps
  (lambda (ls k)
    (cond
      [(pair? ls) (cons-cell-count-cps (car ls) (lambda (v)
                                                      (cons-cell-count-cps (cdr ls) (lambda (w)
                                                                                      (k (add1 (+ v w)))))))]
      [else (k 0)])))
 

(define walk-cps
  (lambda (v ls k)
    (cond
      [(symbol? v) (let ([p  (assq v ls)])
                      (cond
                        [p (walk-cps (cdr p) ls k)]
                        [else (k v)]))]
      [else (k v)])))

(define ack
  (trace-lambda main (m n)
      (cond
            [(zero? m) (add1 n)]
                  [(zero? n) (ack (sub1 m) 1)]
                        [else (ack (sub1 m)
                                         (ack m (sub1 n)))])))

(define ack-cps
  (trace-lambda main (m n k)
    (cond
      [(zero? m) (k (add1 n))]
      [(zero? n) (ack-cps (sub1 m) 1 k)]
      [else (ack-cps m (sub1 n) (lambda (v) 
                                (ack-cps (sub1 m) v k)
                              ))])))


(define fib
  (lambda (n)
      ((lambda  (fib)
        (fib fib n))
         (trace-lambda inner (fib n)
           (cond
            [(zero? n) 0]
            [(= 1 n) 1]
            [else (+ (fib fib (sub1 n)) (fib fib (sub1 (sub1 n))))])))))

(define fib-cps
  (lambda (n k)
    ((lambda (fib-cps k)
      (fib-cps fib-cps n k))
        (lambda (fib-cps n k)
          (cond
          [(zero? n) (k 0)]
          [(= 1 n) (k 1)]
          [(fib-cps fib-cps (sub1 n) (lambda (v) 
                              (fib-cps fib-cps (sub1 (sub1 n)) (lambda (w)
                                                         (k (+ v w))))))])) k)))

(define pascal
  (lambda (n)
   (let ((pascal
     (lambda (pascal)
      (trace-lambda inner (m a)
       (cond
        [(> m n) '()]
        [else (let ((a (+ a m)))
                               (cons a ((pascal pascal) (add1 m) a)))])))))
    ((pascal pascal) 1 0))))

(define pascal-cps
  (lambda (n k)
    (let ([pascal-cps
                  (lambda  (pascal-cps k)
                    (lambda (m a k2)
                      (cond
                        [(> m n)  (k '())]
                        [else (let ((a (+ a m)))
                                                ((pascal-cps pascal-cps (lambda (v)
                                                                              (k (cons a v)))) (add1 m) a k2))])))])
    ((pascal-cps pascal-cps k) 1 0 k))))


(define empty-s
  (lambda ()
      '()))
 
 (define extend-s
   (lambda (x v s)
       (cons `(,x . ,v) s)))

 (define unify
   (lambda (v w s)
    (let ([v (walk v s)])
     (let ([w (walk w s)])
      (cond
       [(eq? v w) s]
       [(symbol? v) (extend-s v w s)]
       [(symbol? w) (extend-s w v s)]
       [(and (pair? v) (pair? w))
                    (let ((s (unify (car v) (car w) s)))
                    (cond
                     [s (unify (cdr v) (cdr w) s)]
                     [else #f]))]
      [(equal? v w) s]
      [else #f])))))

 (define unify-cps
  (lambda (v w s k)
    (walk-cps v s (lambda (wv) (walk-cps w s (lambda (ww)
       (let ([v wv]) 
         (let ([w ww])
           (cond
          [(eq? v w) (k s)]
          [(symbol? v) (k (extend-s v w s))]
          [(symbol? w) (k (extend-s w v s))]
          [(and (pair? v) (pair? w))
                      (unify-cps (car v) (car w) s (lambda (uv)
                      (let ([s uv])
                      (cond
                      [s (unify-cps (cdr v) (cdr w) s k)]
                      [else (k #f)]))))]
          [(equal? v w) (k s)]
          [else (k #f)])))))))))

 (define M
  (lambda (f)
   (lambda (ls)
    (cond
     ((null? ls) '())
       (else (cons (f (car ls)) ((M f) (cdr ls))))))))

 (define M-cps
  (lambda (f k)
   (lambda (ls k)
    (cond
    [(null? ls) (k '())]
    [else ((M-cps f k) (cdr ls) (lambda (v)
                                  (f (car ls) (lambda (fv)
                                                (k (cons fv v))))))]))))

 (define use-of-M
   ((M (lambda (n) (add1 n))) '(1 2 3 4 5)))


(define use-of-M-cps
  ((M-cps (lambda (n k)
            (k (add1 n))) (empty-k)) '(1 2 3 4 5) (empty-k)))
        

 (define strange
  (lambda (x)
   ((lambda (g) 
            (lambda (x) (g g))) (lambda (g) (lambda (x) (g g))))))

(define use-of-strange
  (let ([strange^ (((strange 5) 6) 7)])
      (((strange^ 8) 9) 10)))

 (define strange-cps
 (lambda  (x k)
    ( (lambda  (g k)  (lambda  (x k)  (k (g g k))))
      (lambda  (g k)  (lambda  (x k) (k (g g k)))) k)))

 (define use-of-strange-cps
        (let ([strange^ (((strange-cps 5 (empty-k)) 6 (empty-k)) 7 (empty-k))])
        (((strange^ 8 (empty-k)) 9 (empty-k)) 10 (empty-k))))


 (define use-of-strange-cps-test
        (let ([strange^ (((strange-cps 5 (empty-k)) 6  (empty-k)) 7 (empty-k))])
        (((strange^ 8  (empty-k)) 9 (empty-k)) 10 (empty-k))))


(define why
 (lambda (f)
  ((lambda (g)
    (f (lambda (x) ((g g) x))))
        (lambda (g)
         (f (lambda (x) ((g g) x)))))))

(define why-cps
  (trace-lambda y (f k)
    ((trace-lambda g (g k)
      (f (lambda (x k)  ((g g k) x k)) k))
        (trace-lambda g2 (g k)
          (f (lambda (x k) ((g g k) x k)) k)) k)))


   ;; (f (lambda (fv)
     ;;       (k (fv (lambda (x k) ((g (lambda (gvf)
       ;;                                       (lambda (gvg)
         ;;                                      (k (gvf gvg))))) x k)))))))

      ;;  (lambda (g)
       ;;  (f (lambda (x) ((g g) x)))) k)))
    ; (lambda (g k)
     ;  (f (lambda (fv)
      ;       (k fv (lambda (x k) ((g (lambda (gvf)
       ;                                       (lambda (gvg)
        ;                                       (k (gvf gvg))))) x k)))))) k)))
 
(define almost-length-cps
 (trace-lambda len (f k)
   (trace-lambda lis (ls k)
     (if (null? ls)
         (k 0)
     (f (cdr ls) (lambda (fv) (k (add1 fv))))))))
  
 ;; ((why almost-length) '(a b c d e))

(define almost-length
  (lambda (v)
    (lambda (ls)
      (if (null? ls)
            0
       (add1 (v (cdr ls)))))))
