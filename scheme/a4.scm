 (load "test.scm")
 (load "pmatch.scm")
 
 
 (define closure1-fn
    (lambda (x body env)
      (lambda (a)
        (value-of-fn body (extend-env-fn x a env)))))

 (define apply-env-fn
    (lambda (env x)
      (env x)))
 	  
 (define extend-env-fn
    (lambda (x a env)
      (lambda (y)
        (if (eqv? y x)
             a 
            (apply-env-fn env y)))))
			
 (define empty-env
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
        [`(lambda (,x) ,body)  (closure1-fn x body env)]
        [`(let ((,x ,value)) ,body) (value-of-fn body (extend-env-fn x (value-of-fn value env) env))]
        [`(,rator ,rand) (apply-closure1-fn (value-of-fn rator env) (value-of-fn rand env))])))
		
		
(define apply-closure1-fn
    (lambda (clos a)
      (clos a)))		

	  
	  
(define apply-env-ds
 (lambda (env y)
  (pmatch-who "apply-env-data-struct" env
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
        [`(if ,t ,c ,a) (if (value-of-ds t env) (value-of-ds c env) (value-of-ds a env))]
        [`(lambda (,x) ,body)  (closure1-ds x body env)]
        [`(let ((,x ,value)) ,body) (value-of-ds body (extend-env-ds x (value-of-ds value env) env))]
        [`(,rator ,rand) (apply-closure1-ds (value-of-ds rator env) (value-of-ds rand env))]
        )))	 

(define apply-closure1-ds
(lambda (clos a)
(pmatch-who "apply-closure1-ds" clos
[`(closure1-ds ,x ,body ,env) (value-of-ds body (extend-env-ds x a env))])))
		
 (define closure1-ds
    (lambda (x body env)
      `(closure1-ds ,x ,body ,env)))


;;Both Dymanic scope Interpreter

 (define closure-both
    (lambda (x body env flag)
      (lambda (a env^)
	(cond
	[(eqv? flag 0) (value-of-both body (extend-env x a env^))]
        [ else (value-of-both body (extend-env x a env))]))))

 (define apply-env
    (lambda (env x)
      (env x)))

 (define extend-env
    (lambda (x a env)
      (lambda (y)
        (if (eqv? y x)
            a
            (apply-env env y)))))

 (define empty-env
    (lambda ()
       (lambda (y)
           (errorf 'value-of-both "unbound variable ~s" y))))

  (define value-of-both
    (lambda (exp env)
      (pmatch-who "interpreter-both" exp
       	[`,x (guard (symbol? x)) (apply-env env x)]
     	 [`(quote ()) '()]
     	 [`(null? ,ls) (null? (value-of-both ls env))]
     	 [`(cons ,a ,d) (cons (value-of-both a env) (value-of-both d env))]
     	 [`(car ,ls) (car (value-of-both ls env))]
      [`(cdr ,ls) (cdr (value-of-both ls env))]
        [` ,n (guard (number? n))  n]
        [` ,b-exp (guard (boolean? b-exp)) b-exp]
        [`(sub1 ,body) (sub1 (value-of-both body env))]
        [`(zero? ,body) (zero? (value-of-both body env))]
        [`(* ,n1 ,n2) (* (value-of-both n1 env) (value-of-both n2 env))]
        [`(if ,t ,c ,a) (if (value-of-both t env) (value-of-both c env) (value-of-both a env))]
        [`(lambda (,x) ,body) (closure-both x body env (value-of-both 1 env))]
	[`(d-lambda (,x) ,body) (closure-both x body env (value-of-both 0 env))]
        [`(let ((,x ,value)) ,body) (let ([a (value-of-both value env)]) (value-of-both body (extend-env x a env)))]
        [`(,rator ,rand) (apply-closure-both (value-of-both rator env) (value-of-both rand env) env)])))


(define apply-closure-both
    (lambda (clos a env)
      (clos a env)))


;;Value-of-ri -------------------------------------------------------------------------------------------------------------------

 (define closure-fn
    (lambda (x body env extend-env apply-env closure apply-closure)
      (lambda (a)
	 ((value-of-ri (extend-env x a env) extend-env apply-env closure apply-closure) body))))

(define apply-closure-fn
    (lambda (clos a)
      (clos a)))

(define value-of-ri 
	(lambda (empty-env extend-env apply-env closure apply-closure)
		(lambda (exp)
			(pmatch-who "combined"  exp
			[` ,x (guard (symbol? x)) (apply-env empty-env  x)]
			[` ,n (guard (number? n)) n]
			 [` ,b-exp (guard (boolean? b-exp)) b-exp]
        		[`(sub1 ,body) (sub1 ((value-of-ri empty-env extend-env apply-env closure apply-closure) body))]
       			 [`(zero? ,body) (zero? ((value-of-ri empty-env extend-env apply-env closure apply-closure) body) )]
       			 [`(* ,n1 ,n2) (* ((value-of-ri empty-env extend-env apply-env closure apply-closure) n1) ((value-of-ri empty-env extend-env apply-env closure apply-closure) n2))]
        		[`(if ,t ,c ,a) (if ((value-of-ri empty-env extend-env apply-env closure apply-closure) t) ((value-of-ri empty-env extend-env apply-env closure apply-closure) c) ((value-of-ri empty-env extend-env apply-env closure apply-closure) a))]
	       		 [`(let ((,x ,value)) ,body) ((value-of-ri (lambda (y) (if (eqv? y x) ((value-of-ri empty-env extend-env apply-env closure apply-closure)  value) (env y))) extend-env apply-env closure apply-closure) body)]
			[`(lambda (,x) ,body) (closure x body empty-env extend-env apply-env closure apply-closure)]
			[`(,rator ,rand) (apply-closure ((value-of-ri empty-env extend-env apply-env closure apply-closure) rator) ((value-of-ri empty-env extend-env apply-env closure apply-closure) rand))]))))

(define apply-closure-ds
	(lambda (clos a)
	(pmatch-who "apply-closure-ds" clos
		[`(closure-ds ,x ,body ,env ,extend-env ,apply-env ,closure ,apply-closure) ((value-of-ri (extend-env x a env) extend-env apply-env closure apply-closure) body)])))

 (define closure-ds
    (lambda (x body env extend-env apply-env closure apply-closure)
      `(closure-ds ,x ,body ,env ,extend-env ,apply-env ,closure ,apply-closure)))



