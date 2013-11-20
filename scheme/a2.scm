 (load "pmatch.scm")
 (load "test.scm")
 
 (define list-ref
    (lambda (ls n)
      (letrec ((nth-cdr (lambda (x)
                          (cond
                            [(zero? x) ls]
                            [else (cdr (nth-cdr (- x 1)))]))
                 )) (car (nth-cdr n)))))
 
 
 (define lambda->lumbda
    (lambda (exp)
      (pmatch-who "lambda->lumbda" exp
        [`,a (guard (symbol? a)) a]
        [`(lambda (,a) ,body) `(lumbda (,a) ,(lambda->lumbda body))]
        [`(,rator ,rand) `( ,(lambda->lumbda rator) ,(lambda->lumbda rand))]
        )))
		


  (define vars
    (lambda (x)
      (pmatch-who "the-vars" x
      [` ,x (guard (symbol? x)) `(,x)]
      [` (lambda (,x) ,body) (vars body)]
      [` (,rator ,rand) (append (vars rator) (vars rand))]
        )))
		
 #| (define union
    (lambda (x y)
      (cond
        [(null? x) '()]
        [(null? y) '()]
        [(eqv? (not (memv (car x) (cdr x))) #f) (union (remv (car x) (cdr x)) y)]
        [(eqv? (not(memv (car y) x)) #f) (union x (cdr y))]
        [else (append y (union x (cdr y))))]))) |#
		
(define union
    (lambda (x y)
      (pmatch y
        [`() x]
        ;;[(eqv? (not (memv (car x) (cdr x))) #f) (union (remv (car x) (cdr x)) y)]
        [` ,y (guard(and (symbol? y) (eqv? (not(memv y x)) #f)))  (union  x '())]
        [` ,y (guard(and (symbol? y) (eqv? (memv y x) #f))) x]
        [` (,y . ,d) (guard(and (symbol? y) (eqv? (not(memv y x)) #f))) (union x d)] 
        [ ` (,y . ,d) (guard(and (symbol? y) (eqv? (memv y x) #f))) (append (list y) (union x d))]
        )))
		
(define unique-vars
    (lambda (x)
      (pmatch-who "the-vars-unique" x
      [` ,x (guard (symbol? x)) `(,x)]
      [` (lambda (,x) ,body) (unique-vars body)]
      [` (,rator ,rand) (union (unique-vars rator) (unique-vars rand))]
        )))

(define extend 
    (lambda (x pred)
      (lambda (n)
        (cond
          [(or (eqv? x n) (pred n)) #t]
          [else #f]))))
		  
   (define free?
    (lambda (obj exp)
      (pmatch exp
        [`,x (guard (symbol? x)) (eqv? obj x)]
        [`(,x) (guard (symbol? x)) (eqv? obj x) ]
        [`(lambda (,x) ,body) (cond 
                                [(and (and (not (eqv? obj x)) (memv obj  body)) (free? obj body)) #t]
                                [else #f])]
        [`(,x . ,d) (or (free? obj x) (free? obj d))]
        [else #f])))
		
		
	 (define bound?
    (lambda (obj exp)
      (pmatch exp
        [`,x (guard (symbol? x)) #f ]
        [`(,x) (guard (symbol? x)) #f ]
        [`(lambda (,x) ,body) (cond
                                [(and (eqv? obj x) (free? obj body)) ]
                                [else (bound? obj body)]
                               )]
        [`(,rator ,rand) (or (bound? obj rator) (bound? obj rand))])))	

 (define free
    (lambda (exp)
      (pmatch exp
        [` () '()]
        [`,x (guard (symbol? x)) `(,x)]
        [`(lambda (,x) ,body) (remv x (free body))]
        [`(,rator ,rand) (union (free rator) (free rand))])))

 (define bound
     (lambda (exp)
       (pmatch exp
         [`() '()]
         [`,x (guard (symbol? x)) '()]
        [`(,x) (guard (symbol? x)) '()]
         [`(lambda (,x) ,body) (cond
                                 [(memv x (unique-vars body)) (append `(,x) (bound body))]
                                 [else (bound body)])]
         [`(,rator ,rand) (union (bound rator) (bound rand))])))	
		 

 (define walk-symbol 
    (lambda (obj exp)
		(cond
             [(eqv? (assv obj exp) #f) obj]
             [else (walk-symbol (cdr (assv obj exp)) exp)])))
			 
 (define lex
    (lambda (exp acc)
      (pmatch exp
        [`,x (guard(symbol? x)) (cond
                                  [(not (memv x acc)) `(free-var ,x)]
                                  [else `(var ,(- (length acc) (length (memv x acc))))])]
        [`(lambda (,x) ,body) `(lambda ,(lex body (cons x acc)))]
        [`(,rator ,rand) `(,(lex rator acc) ,(lex rand acc))])))
