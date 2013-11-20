 (load "pmatch.scm")
 (load "test.scm")
 
 (define value-of
    (lambda (exp env)
      (pmatch-who "interpreter" exp
        [` ,x (guard (symbol? x)) (env x)]
        [` ,n (guard (number? n))  n]
        [` ,b-exp (guard (boolean? b-exp)) b-exp]
        [`(sub1 ,body) (sub1 (value-of body env))]
        [`(zero? ,body) (zero? (value-of body env))]
        [`(* ,n1 ,n2) (* (value-of n1 env) (value-of n2 env))]
        [`(if ,t ,c ,a) (if (value-of t env) (value-of c env) (value-of a env))]
        [`(lambda (,x) ,body) (lambda (a) (value-of body (lambda (y) (if (eqv? x y) a (env y)))))]
        [`(let ((,x ,value)) ,body) (let ((a (value-of value env))) (value-of body (lambda (y) (if (eqv? y x) a (env y)))))]
        [`(,rator ,rand) ((value-of rator env) (value-of rand env))])))

		
 (define fo-eulav
    (lambda (exp env)
      (pmatch-who "inverse" exp
        [` ,x (guard(symbol? x)) (env x)]
        [` ,n (guard(number? n)) n]
        [` (,body 1bus) (sub1 (fo-eulav body env))]
        [` (,a ,c ,t fi)  (if (fo-eulav t env) (fo-eulav c env) (fo-eulav a env))]
        [` (,body ?orez) (zero? (fo-eulav body env))]
         [`(,n1 ,n2 *) (* (fo-eulav n1 env) (fo-eulav n2 env))]
        [`(,body (,x) adbmal) (lambda (a) (fo-eulav body (lambda (y) (if (eqv? x y) a (env y)))))]
        [`(,rand ,rator) ((fo-eulav rator env) (fo-eulav rand env))])))
		
	 
	 ;;Higher order functions
(define apply-env-fn
    (lambda (env x)
      (env x)))
	  
 (define extend-env-fn
    (lambda (x a env)
      (lambda (y)
        (if (eqv? y x)
            a 
            (apply-env-fn env y)))))
			
 (define empty-env-fn
    (lambda ()
       (lambda (y) 
	   (errorf 'value-of "unbound variable ~s" y))))	

 (define value-of-fn
    (lambda (exp env)
      (pmatch-who "interpreter" exp
        [` ,x (guard (symbol? x)) (apply-env-fn env x)]
        [` ,n (guard (number? n))  n]
        [` ,b-exp (guard (boolean? b-exp)) b-exp]
        [`(sub1 ,body) (sub1 (value-of-fn body env))]
        [`(zero? ,body) (zero? (value-of-fn body env))]
        [`(* ,n1 ,n2) (* (value-of-fn n1 env) (value-of-fn n2 env))]
        [`(if ,t ,c ,a) (if (value-of-fn t env) (value-of-fn c env) (value-of-fn a env))]
        [`(lambda (,x) ,body)  (lambda (a) (value-of-fn body (extend-env-fn x a env)))]
        [`(let ((,x ,value)) ,body) (value-of-fn body (extend-env-fn x (value-of-fn value env) env))]
        [`(,rator ,rand) ((value-of-fn rator env) (value-of-fn rand env))]
        )))
		
 (define value-of
    (lambda (exp env)
      (pmatch-who "interpreter" exp
        [`,x (guard (symbol? x)) (env x)]
        [`,n (guard (number? n))  n]
        [`,b-exp (guard (boolean? b-exp)) b-exp]
        [`(sub1 ,body) (sub1 (value-of body env))]
        [`(zero? ,body) (zero? (value-of body env))]
        [`(* ,n1 ,n2) (* (value-of n1 env) (value-of n2 env))]
        [`(if ,t ,c ,a) (if (value-of t env) (value-of c env) (value-of a env))]
	    	[`(begin2 ,exp1 ,exp2) (begin (value-of exp1 env) (value-of exp2 env))]
	    	[`(set! ,x ,value) (set! x (value-of value env))]
        ;;[`(set! ,x ,value) (value-of env (lambda (y) (if (eqv? y x) (set! y (value-of value env)) (env y))))]
	    	;;[`(set! ,x ,value) ((lambda (y) (if(eqv? y x) (value-of value env) (env y))) x)]
        [`(lambda (,x) ,body) (lambda (a) (value-of body (lambda (y) (if (eqv? x y) a (env y)))))]
        [`(let ((,x ,value)) ,body) (value-of body (lambda (y) (if (eqv? y x) (value-of value env) (env y))))]
        [`(,rator ,rand) ((value-of rator env) (value-of rand env))]
        )))

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
        [`(* ,n1 ,n2) (* (value-of-ds n1 env) (value-of-ds n2 env))]
      	[`(+ ,n1 ,n2) (+ (value-of-ds n1 env) (value-of-ds n2 env))]
        [`(if ,t ,c ,a) (if (value-of-ds t env) (value-of-ds c env) (value-of-ds a env))]
        [`(lambda (,x) ,body)  (lambda (a) (value-of-ds body (extend-env-ds x a env)))]
        [`(let ((,x ,value)) ,body) (value-of-ds body (extend-env-ds x (value-of-ds value env) env))]
        [`(,rator ,rand) ((value-of-ds rator env) (value-of-ds rand env))]
        )))
		
(define (extend-env-lex a env)
`(,a . ,env))

(define (extend-env-lex a env)
 `(cons ,a ,env))

(define (apply-env-lex env y)
(pmatch env
 [`() 'empty ]
 [`(cons ,a ,env) (list-ref env y)]))
 
 
