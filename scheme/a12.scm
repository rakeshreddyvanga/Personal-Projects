(load "pmatch.scm")
(load "test.scm")

(define-syntax do
  (syntax-rules (<-)
    ((_ bind e) e)
    ((_ bind (v <- e) e* e** ...)
     (bind e (lambda (v) (do bind e* e** ...))))
    ((_ bind e e* e** ...)
     (bind e (lambda (_) (do bind e* e** ...))))))

(define fact-5
  '((lambda (f)
      ((f f) 5))
    (lambda (f)
      (lambda (n)
        (if (zero? n)
          1
          (* n ((f f) (sub1 n))))))))

(define capture-fun
  '(* 3 (capture q (* 2 (return 4 q)))))

(define return-maybe
  (lambda (a) `(Just ,a)))
 
(define bind-maybe
  (lambda (ma f)
    (cond
      [(eq? (car ma) 'Just) (f (cadr ma))]
      [(eq? (car ma) 'Nothing) '(Nothing)])))
 
(define fail
  (lambda ()
    '(Nothing)))

(define assv 
  (lambda (x ls)
    (cond
      [(null? ls) (fail)]
      [(eq? (car (car ls)) x) (return-maybe (cdr (car ls)))]
      [else (assv x (cdr ls))])))

;;-------Writer Monads----------

(define return-writer
  (lambda (a) `(,a . ())))
 
(define bind-writer
  (lambda (ma f)
    (let ([mb (f (car ma))])
      `(,(car mb) . ,(append (cdr ma) (cdr mb))))))
 
(define tell-writer
  (lambda (msg)
    `(_ . (,msg))))

;;-------------------------------

(define partition
  (lambda (pred ls)
    (cond
      [(null? ls) (return-writer '())]
      [(pred (car ls)) (bind-writer 
                          (tell-writer (car ls))
                          (lambda (_)
                           (partition pred (cdr ls))))]
      [else
        (bind-writer
          (partition pred (cdr ls))
          (lambda (x)
            (return-writer 
            (cons (car ls)  x))))])))


(trace-define power
  (lambda (x n)
    (cond
      [(zero? n) 1]
      [(= n 1) x]
      [(odd? n) (* x (power x (sub1 n)))]
      [(even? n) (let ((nhalf (/ n 2)))
                   (let ((y (power x nhalf)))
                     (* y y)))])))

(define powerXpartials
  (lambda (x n)
    (cond
      [(zero? n) (return-writer 1)]
      [(= n 1) (return-writer x)]
      [(odd? n) (bind-writer
                  (powerXpartials x (sub1 n))
                  (lambda (ans)
                   ` (,(* x ans) . (,ans))))]
      [(even? n) (let [(nhalf (/ n 2))]
                    (bind-writer
                      (powerXpartials x nhalf)
                      (lambda (ans)
                       `(,(* ans ans) . (,ans)))))])))
 
;;------State Monads------
(define return-state
  (lambda (a)
    (lambda (s)
      `(,a . ,s))))


(define bind-state
  (lambda (ma f)
    (lambda (s)
      (let ([vs^ (ma s)])
        (let ([v (car vs^)]
              [s^ (cdr vs^)])
          ((f v) s^))))))


(define get-state
  (lambda (s) `(,s . ,s)))


(define put-state
  (lambda (new-s)
    (lambda (s)
      `(_ . ,new-s))))

;;------------------------

(define abc-game
  (lambda (ls)
     (cond
      [(null? ls) (return-state '_)]
      [(eq? (car ls) 'a) (bind-state
                         (abc-game (cdr ls))
                         (lambda (n)
                          (bind-state
                            get-state
                            (lambda (s)
                              (bind-state
                                (put-state (add1 s))
                                  (lambda (_)
                                    (return-state n)))))))]
     [(eq? (car ls) 'b) (bind-state
                         (abc-game (cdr ls))
                         (lambda (n)
                          (bind-state
                            get-state
                            (lambda (s)
                              (bind-state
                                (put-state (sub1 s))
                                  (lambda (_)
                                    (return-state n)))))))]
      [else (bind-state
              (abc-game (cdr ls))
                (lambda (n)
                  (return-state n)))])))

;;-----------------Traverse----------------
(define traverse
  (lambda (return bind f)
    (letrec
      ((trav
         (lambda (tree)
           (cond
             [(pair? tree)
              (do bind
                (a <- (trav (car tree)))
                (d <- (trav (cdr tree)))
                (return (cons a d)))]
             [else (f tree)]))))
      trav)))

;;-----------------------------------------

(define reciprocal
  (lambda (x)
    (cond
      [(zero? x) (fail)]
      [else (return-maybe (/ 1 x))])))


(define traverse-reciprocal
  (traverse return-maybe bind-maybe reciprocal))
;;-------------------------------------------
(define halve
  (lambda (n)
    (cond
      [(even? n) (bind-writer
                  (return-writer (/ n 2))
                  (lambda (ans)
                   (return-writer ans)))]
      [(odd? n) (bind-writer
                (return-writer n)
                (lambda (ans)
                `(,ans . (,ans))))])))

(define traverse-halve
  (traverse return-writer bind-writer halve))

(define state/sum
  (lambda (n)
    (bind-state
      (return-state n)
      (lambda (n^)
       (bind-state
        get-state
         (lambda (s)
          (bind-state
            (put-state (+ n^ s))
            (lambda (_)
              (return-state s)))))))))

(define traverse-state/sum
  (traverse return-state bind-state state/sum))

;;-----------------Brain Teaser------------------

(define return-cont
  (lambda (a)
    (lambda (k)
      (k a))))
 
(define bind-cont
  (lambda (ma f)
    (lambda (k)
      (let ((k^ (lambda (a)
                  (let ((mb (f a)))
                    (mb k)))))
        (ma k^)))))

(define callcc
  (lambda (g)
    (lambda (k)
      (let ((k-as-proc (lambda (a) (lambda (k_ignored) (k a)))))
        (let ((ma (g k-as-proc)))
          (ma k))))))


 (define value-of-cps
 (lambda (expr env)
   (pmatch expr
     [`,n (guard (or (number? n) (boolean? n))) (return-cont n)]
     [`(+ ,x1 ,x2) (bind-cont (value-of-cps x1 env) (lambda (n1) (bind-cont (value-of-cps x2 env) (lambda (n2) (return-cont (+ n1 n2))))))]
     [`(* ,x1 ,x2) (bind-cont (value-of-cps x1 env) (lambda (n1) (bind-cont (value-of-cps x2 env) (lambda (n2) (return-cont (* n1 n2))))))]
     [`(sub1 ,x) (bind-cont (value-of-cps x env) (lambda (n) (return-cont (- n 1))))]
     [`(zero? ,x) (bind-cont (value-of-cps x env) (lambda (n) (return-cont (zero? n))))]
     [`(if ,test ,conseq ,alt) (bind-cont (value-of-cps test env) (lambda (t) (if t
                                                                    (bind-cont (value-of-cps conseq env) (lambda (c) (return-cont c)))
                                                                    (bind-cont (value-of-cps alt env) (lambda (a) (return-cont a))))))]
     [`(capture ,k-id ,body) (callcc (lambda (k)
                                      (bind-cont (value-of-cps body (extend-env k-id env k)) (lambda (v) (return-cont v)))))]
     [`(return ,v-exp ,k-exp) (bind-cont (value-of-cps k-exp env) (lambda (k) (bind-cont (value-of-cps v-exp env) (lambda (v) (k v)))))]
     [`,x (guard (symbol? x)) (bind-cont (apply-env env x) (lambda (sym)  sym))]
     [`(lambda (,id) ,body) (return-cont (closure id body env))]
     [`(,rator ,rand) (bind-cont (value-of-cps rator env) (lambda (clos) (bind-cont (value-of-cps rand env) (lambda (a) (apply-closure clos a)))))])))


 (define empty-env
  (lambda ()
    (lambda (y) 
      (errorf 'value-of "unbound variable ~s" y))))

(define apply-env
  (lambda (env x)
     (return-cont (env x))))

  (define apply-cluosure
    (lambda (clos v)
     (clos v)))

(define closure
      (lambda (id body env)
        (lambda (a)
          (bind-cont (value-of-cps body (extend-env id env a)) (lambda (v)  (return-cont v))))))

 (define extend-env
      (lambda (id env a)
        (lambda (y)
          (if (eqv? y id)
              (return-cont a)
              (apply-env env y)))))

