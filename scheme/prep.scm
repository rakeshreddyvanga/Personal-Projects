
(load "pmatch.scm")

(define x 5)

(define countdown
	(lambda (x)
		(cond
		[(zero? x) '(0)]
		[else (cons x (countdown (- x 1)))])))

(define member-?*
	(lambda (ls)
		(cond
		[(null? ls) #f]
		[(pair? (car ls)) (or (member-?* (car ls)) (member-?* (cdr ls)))]
		[(eqv? '? (car ls)) #t]
		[else (member-?* (cdr ls))])))


(define trib 
	(lambda (n)
		(cond
		[(zero? n) 0]
		[(zero? (- n 1)) 0]
		[(zero? (- n 2)) 1]
		[else (+ (+ (trib (- n 1)) (trib (- n 2))) (trib (- n 3)))])))

(define-syntax cons$
  (syntax-rules ()
    ((cons$ x y) (cons x (delay y)))))
 
(define car$ car)
 
(define cdr$
  (lambda ($) (force (cdr $))))

(define trib$
  (cons$ 0 (cons$ 1 (cons$ 's 's ))))

(define inf-ls
  (cons$ 1 inf-ls))

  

  (define take$
    (lambda (n $)
        (cond
              ((zero? n) '())
                    (else (cons (car$ $) (take$ (sub1 n) (cdr$ $)))))))

(define merge
	(lambda (ls1 ls2)
		(pmatch `(,ls1 ,ls2)
		[`(,ls1 ()) ls1]
		[`(() ,ls2) ls2]
		[`(,ls1  ,ls2) (guard(and (pair? ls1) (pair? ls2))) (cond
				[(> (car ls1) (car ls2)) (cons (car ls2)  (merge  ls1 (cdr ls2)))]
				[else (cons (car ls1)  (merge (cdr ls1) ls2))])])))


(define bound-count
	(lambda (exp env)
		(pmatch exp
			[`,x (guard (symbol? x)) (if (memq x env) 1 0)]
			[`(lambda (,x) ,e) (bound-count e (cons x env))]
			[`(,e1 ,e2) (+ (bound-count e1 env) (bound-count e2 env))])))

(define mirror 
	(lambda (x)
		(cond
			[(not (pair? x)) x]
			[else (cons (mirror (cdr x)) (mirror (car x)))])))


	  

(define apply-env-ds

 (lambda (env y)

  (pmatch-who "apply-env-data structure" env

  [`() (errorf 'value-of-ds "unbound variable ~s" y)]

  [`((,x . ,a) . ,env) (if (eqv? y x) a (apply-env-ds env y))])))



(define empty-env

  (lambda ()

    `()))





(define extend-env-ds

  (lambda (x a env)

    `((,x . ,a) . ,env)))

	

(define value-of-ds

    (lambda (exp env)

      (pmatch-who "interpreter" exp

        [` ,x (guard (symbol? x)) (apply-env-ds env x)]

        [` ,n (guard (number? n))  n]

        [` ,b-exp (guard (boolean? b-exp)) b-exp]

        [`(sub1 ,body) (sub1 (value-of-ds body env))]

        [`(zero? ,body) (zero? (value-of-ds body env))]
	[` (+ ,n1 ,n2) (+ (value-of-ds n1 env) (value-of-ds n2 env))]
        [`(* ,n1 ,n2) (* (value-of-ds n1 env) (value-of-ds n2 env))]
        [`(if ,t ,c ,a) (if (value-of-ds t env) (value-of-ds c env) (value-of-ds a env))]
;;	[`(let*2 ([,x1 ,e1] [,x2 ,e2]) ,body) (let ([env (extend-env-ds x1 (value-of-ds e1 env) env)])) (value-of-ds body (extend-env-ds x2 (value-of-ds e2 env)))]
        [`(lambda (,x) ,body)  (closure-ds x body env)]

        [`(let ((,x ,value)) ,body) (value-of-ds body (extend-env-ds x (value-of-ds value env) env))]

        [`(,rator ,rand) (apply-closure-ds (value-of-ds rator env) (value-of-ds rand env))]

       )))	 



(define apply-closure-ds

(lambda (clos a)

(pmatch-who "apply-closure-ds" clos

[`(closure-ds ,x ,body ,env) (value-of-ds body (extend-env-ds x a env))])))


(define minus
	(lambda (n1 n2)
	 (cond
	     [(zero? n2) n1]
	     [else (sub1 (minus n1 (sub1 n2)))])))

(define memv
	(lambda (x ls)
		(pmatch-who "the-memv" ls
			[`() #f]
			[`,a (guard (symbol? a)) (if (eqv? a x) a #f)]
			[`(,a . ,d) (if (eqv? a x) ls (memv x d))])))

(define intersection
	(lambda (s1 s2)
		(cond
			[(null? s1) '()]
                        [(null? s2) '()]
			[(eqv? (not (memv (car s1) s2)) #f) (append (list (car s1)) (intersecrtion (cdr s1) s2))]
			[else (intersection (cdr s1) s2)])))

(define reverse
	(lambda (ls)
		(cond
			[(null?  ls)  '()]
			[else (let ([x  (car ls)]) (cons  (reverse (cdr ls))  x))])))

                       
                       
    ((lambda (q)
     ((lambda (q r)
        (q (r q 5)))
     (lambda (p) p)
     (lambda (u t) (u q))))
   4)

   (define empty-k
  (lambda ()
    (let ((once-only #f))
      (lambda (v)
        (if once-only 
      (errorf 'empty-k "You can only invoke the empty continuation once")
      (begin (set! once-only #t) v))))))

(define subst
  (lambda (x y ls k)
    (cond
      [(null? ls) (app-k-subst k '())]
      [(eq? (car ls) x) (subst x y (cdr ls) (eq-k-subst y k))]
      [else (subst x y (cdr ls) (else-k-subst ls k))])))
    
(trace-define app-k-subst
  (lambda (k v)
    (pmatch k
      [`(empty-k-subst) v]
      [`(eq-k-subst ,y ,k) (k (cons y v))]
      [`(else-k-subst ,ls ,k) (k (cons (car ls) v))])))

(define eq-k-subst
  (lambda (y k)
   `(eq-k-subst ,y ,k)))

(define else-k-subst
  (lambda (ls k)
   `(else-k-subst ,ls ,k)))
  
(define empty-k-subst
  (lambda ()
    `(empty-k-subst)))



(trace-define pascal
  (lambda (n)
   (let ((pascal
     (lambda (pascal)
      (lambda  (m a)
       (cond
        [(> m n) '()]
        [else (let ((a (+ a m)))
                               (cons a ((pascal pascal) (add1 m) a)))])))))
    ((pascal pascal) 1 0))))

(trace-define pascal-cps
  (lambda (n k)
    (let ((pascal-cps (lambda (pascal-cps k)
                          (k (lambda (m a k)
                            (cond
                              [(> m n) (k '())]
                              [else (let ((a (+ a m)))
                                (pascal-cps pascal-cps (lambda (f)
                                                  (f (add1 m) a (lambda (v)
                                                               (k (cons a v)))))))]))))))
    (pascal-cps pascal-cps (lambda (v) (v 1 0 k))))))


                  
 (trace-define M-cps
  (lambda (f k)
    (k (lambda (ls k)
      (cond
        [(null? ls) (k '())]
        [else (M-cps f (lambda (v) 
                           (f (car ls) (lambda (v1)
                                        (v (cdr ls) (lambda (v2)
                                        (k (cons v1 v2))))))))])))))


(define strange
  (lambda (x)
    ((lambda (g) (lambda (x) (g g)))
     (lambda (g) (lambda (x) (g g))))))
                              
