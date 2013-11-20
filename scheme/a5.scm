(load "pmatch.scm")
(load "test.scm")


(define val-of-cbv
  (lambda (exp env)
    (pmatch exp
      [`,b (guard (boolean? b)) b]
      [`,n (guard (number? n)) n]
      [`(zero? ,n) (zero? (val-of-cbv n env))]
      [`(sub1 ,n) (sub1 (val-of-cbv n env))]
      [`(* ,n1 ,n2) (* (val-of-cbv n1 env) (val-of-cbv n2 env))]
      [`(if ,test ,conseq ,alt) (if (val-of-cbv test env)
                                  (val-of-cbv conseq env)
                                  (val-of-cbv alt env))]
      [`(begin2 ,e1 ,e2) (begin (val-of-cbv e1 env) (val-of-cbv e2 env))]
      [`(set! ,x ,value) (guard (symbol? x))  (set-box! (apply-env env x) (val-of-cbv value env) )]
      [`(random ,n) (random (val-of-cbv n env))]
      [`,x (guard (symbol? x)) (unbox (apply-env env x))]
      [`(let ((,x ,value)) ,body) (let ((a (box (value-of-cbv value env)))) (value-of body (extend-env x a env)))] 
      [`(lambda (,x) ,body) (closure-cbv x body env)]
      [`(,rator ,x) (guard (symbol? x)) (apply-closure (val-of-cbv rator env) (box (unbox (apply-env env x))))]
      [`(,rator ,rand) (apply-closure (val-of-cbv rator env) (box (val-of-cbv rand env)))])))


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
           (errorf 'val-of "unbound variable ~s" y))))

 (define closure-cbv
    (lambda (x body env)
      (lambda (a)
        (val-of-cbv body (extend-env x a env)))))



(define apply-closure
    (lambda (clos a)
      (clos  a)))


 (define closure-cbr
    (lambda (x body env)
      (lambda (a)
        (val-of-cbr body (extend-env x a env)))))


(define val-of-cbr
  (lambda (exp env)
    (pmatch exp
      [`,b (guard (boolean? b)) b]
      [`,n (guard (number? n)) n]
      [`(zero? ,n) (zero? (val-of-cbr n env))]
      [`(sub1 ,n) (sub1 (val-of-cbr n env))]
      [`(* ,n1 ,n2) (* (val-of-cbr n1 env) (val-of-cbr n2 env))]
      [`(if ,test ,conseq ,alt) (if (val-of-cbr test env)
                                  (val-of-cbr conseq env)
                                  (val-of-cbr alt env))]
      [`(begin2 ,e1 ,e2) (begin (val-of-cbr e1 env) (val-of-cbr e2 env))]
      [`(set! ,x ,value) (guard (symbol? x))  (set-box! (apply-env env x) (val-of-cbr value env) )]
      [`(random ,n) (random (val-of-cbr n env))]
      [`,x (guard (symbol? x)) (unbox (apply-env env x))]
      [`(lambda (,x) ,body) (closure-cbr x body env)]
      [`(let ((,x ,value)) ,body) (let ((a (box (value-of-cbr value env)))) (value-of body (extend-env x a env)))] 
      [`(,rator ,x) (guard (symbol? x)) (apply-closure (val-of-cbr rator env) (apply-env env x))]
      [`(,rator ,rand) (apply-closure (val-of-cbr rator env) (box (val-of-cbr rand env)))])))


(define val-of-cbname
  (lambda (exp env)
    (pmatch exp
      [`,b (guard (boolean? b)) b]
      [`,n (guard (number? n)) n]
      [`(zero? ,n) (zero? (val-of-cbname n env))]
      [`(sub1 ,n) (sub1 (val-of-cbname n env))]
      [`(* ,n1 ,n2) (* (val-of-cbname n1 env) (val-of-cbname n2 env))]
      [`(+ ,n1 ,n2) (+ (val-of-cbname n1 env) (val-of-cbname n2 env))]
      [`(if ,test ,conseq ,alt) (if (val-of-cbname test env)
                                  (val-of-cbname conseq env)
                                  (val-of-cbname alt env))]
      [`(random ,n) (random (val-of-cbname n env))]
      [`,x (guard (symbol? x)) ((unbox (apply-env env x)))]
      [`(lambda (,x) ,body) (closure-cbname x body env)]
      [`(,rator ,x) (guard (symbol? x)) (apply-closure (val-of-cbname rator env) (apply-env env x))]
      [`(,rator ,rand) (apply-closure (val-of-cbname rator env) (box (lambda() (val-of-cbname rand env))))])))

(define closure-cbname
    (lambda (x body env)
      (lambda (a)
        (val-of-cbname body (extend-env x a env)))))


(define val-of-cbneed
  (lambda (exp env)
    (pmatch exp
      [`,b (guard (boolean? b)) b]
      [`,n (guard (number? n)) n]
      [`(zero? ,n) (zero? (val-of-cbneed n env))]
      [`(sub1 ,n) (sub1 (val-of-cbneed n env))]
      [`(+ ,n1 ,n2) (+ (val-of-cbneed n1 env) (val-of-cbneed n2 env))]
      [`(* ,n1 ,n2) (* (val-of-cbneed n1 env) (val-of-cbneed n2 env))]
      [`(if ,test ,conseq ,alt) (if (val-of-cbneed test env)
                                  (val-of-cbneed conseq env)
                                  (val-of-cbneed alt env))]
      [`(random ,n) (random (val-of-cbneed n env))]
      [`,x (guard (symbol? x)) (unbox/need (apply-env env x))]
      [`(lambda (,x) ,body) (closure-cbneed x body env)]
      [`(,rator ,x) (guard (symbol? x)) (apply-closure (val-of-cbneed rator env) (apply-env env x))]
      [`(,rator ,rand) (apply-closure (val-of-cbneed rator env) (box (lambda() (val-of-cbneed rand env))))])))

(define closure-cbneed
    (lambda (x body env)
      (lambda (a)
        (val-of-cbneed body (extend-env x a env)))))

(define unbox/need
	(lambda (b)
	  (let ([ val (unbox b)])
      (let ([a (val)])
	       (set-box! b val)
		  a))))


(define val-of-cbden
  (lambda (exp env)
    (pmatch exp
      [`,b (guard (boolean? b)) b]
      [`,n (guard (number? n)) n]
      [`(zero? ,n) (zero? (val-of-cbden n env))]
      [`(sub1 ,n) (sub1 (val-of-cbden n env))]
      [`(* ,n1 ,n2) (* (val-of-cbden n1 env) (val-of-cbden n2 env))]
      [`(if ,test ,conseq ,alt) (if (val-of-cbden test env)
                                  (val-of-cbden conseq env)
                                  (val-of-cbden alt env))]
      [`(+ ,n1 ,n2) (+ (val-of-cbden n1 env) (val-of-cbden n2 env))]
      [`(random ,n) (random (val-of-cbden n env))]
      [`,x (guard (symbol? x)) ((unbox (apply-env env x)) env)]
      [`(lambda (,x) ,body) (closure-cbden x body env)]
      [`(,rator ,x) (guard (symbol? x)) (apply-closure (val-of-cbden rator env) (box (lambda (env) ((unbox (apply-env env x)) env))))]
      [`(,rator ,rand) (apply-closure (val-of-cbden rator env) (box (lambda (env^) (val-of-cbden rand env^))))])))

(define closure-cbden
    (lambda (x body env)
      (lambda (a)
        (val-of-cbden body (extend-env x a env)))))



(define convert
  (lambda (exp)
    (pmatch exp
      [`,atom (guard (not (pair? atom))) atom]
      [`(let ([,x ,e]) ,body) `((lambda (,x) ,(convert body)) ,(convert e))]
      [`,otherwise (cons (convert (car exp)) (convert (cdr exp)))])))



(define val-of-cbv
  (lambda (exp env)
    (pmatch exp
      [`,b (guard (boolean? b)) b]
      [`(quote ()) '()]
      [`(null? ,ls) (null? (val-of-cbv ls env))]
      [`,n (guard (number? n)) n]
      [`(zero? ,n) (zero? (val-of-cbv n env))]
      [`(cons ,exp1 ,exp2) (cons (val-of-cbv exp1 env) (val-of-cbv exp2 env))]
      [`(cons^ ,exp1 ,exp2) (cons (lambda () (val-of-cbv exp1 env)) (lambda () (val-of-cbv exp2 env)))]
      [`(car^ ,exp) ((car (val-of-cbv exp env)))] 
      [`(cdr^ ,exp) ((cdr (val-of-cbv exp env)))]
      [`(car ,exp1) (car (val-of-cbv exp1 env))]
      [`(cdr ,exp) (cdr (val-of-cbv exp env))]
      [`(sub1 ,n) (sub1 (val-of-cbv n env))]
      [`(add1 ,n) (add1 (val-of-cbv n env))]
      [`(* ,n1 ,n2) (* (val-of-cbv n1 env) (val-of-cbv n2 env))]
      [`(if ,test ,conseq ,alt) (if (val-of-cbv test env)
                                  (val-of-cbv conseq env)
                                  (val-of-cbv alt env))]
      [`(begin2 ,e1 ,e2) (begin (val-of-cbv e1 env) (val-of-cbv e2 env))]
      [`(set! ,x ,value) (guard (symbol? x))  (set-box! (apply-env env x) (val-of-cbv value env) )]
      [`(random ,n) (random (val-of-cbv n env))]
      [`,x (guard (symbol? x)) (unbox (apply-env env x))]
      [`(lambda (,x) ,body) (closure-cbv x body env)]
      [`(let ((,x ,value)) ,body) (let ([a (box (val-of-cbv value env))]) (val-of-cbv body (extend-env x a env)))]
      [`(,rator ,x) (guard (symbol? x)) (apply-closure (val-of-cbv rator env) (box (unbox (apply-env env x))))]
      [`(,rator ,rand) (apply-closure (val-of-cbv rator env) (box (val-of-cbv rand env)))])))



(define cons-test
  '(let ((fix (lambda (f)
               ((lambda (x) (f (lambda (v) ((x x) v))))
                (lambda (x) (f (lambda (v) ((x x) v))))))))
      (let ((map (fix (lambda (map)
                        (lambda (f)
                          (lambda (l)
                             (if (null? l)
                                 '()
                                 (cons^ (f (car^ l))
                                        ((map f) (cdr^ l))))))))))
        (let ((take (fix (lambda (take)
                           (lambda (l)
                             (lambda (n)
                               (if (zero? n)
                                   '()
                                    (cons (car^ l) 
                                          ((take (cdr^ l)) (sub1 n))))))))))
          ((take ((fix (lambda (m)
                         (lambda (i)
                           (cons^ 1 ((map (lambda (x) (add1 x))) (m i)))))) 0)) 5)))))
