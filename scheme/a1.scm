;1)
(define countdown
  (lambda (n)
    (if (not (= n 0))
        (cons n (countdown (- n 1)))
       '(0))
    ))

;2)
 (define insertR
    (lambda (x y a)
      (cond
        [(null? a) '()]
        [(eqv? x (car a)) (cons x (cons y (insertR x y (cdr a))))]
        [else (cons (car a) (insertR x y (cdr a)))])))

;3)
  (define remv-1st
    (lambda (x a)
      (cond 
        [(null? a) '()]
        [(eqv? x (car a)) (cdr a)]
        [else (cons (car a) (remv-1st x (cdr a)))])))
;4)
 (define occurs-?s
    (lambda (a)
      (cond
        [(null? a) 0]
	[(pair? (car a)) (+ (occurs-?s (car a)) (occurs-?s (cdr a))) ]
        [(eqv? '? (car a)) (add1 (occurs-?s (cdr a)))]
        [else  (occurs-?s (cdr a))])))

;5)
(define filter
    (lambda (p a)
      (cond 
        [(null? a) '()]
        [(p (car a))(cons (car a) (filter p (cdr a)))]
        [else (filter p (cdr a))])))
;6)
(define zip
    (lambda (x y)
      (cond
        [(not(eqv? (length x) (length y) )) "Lists are not equal"]
        [(null? x) '()]
        [else (cons (cons (car x) (car y)) (zip (cdr x) (cdr y)))])))

;7)
(define map
    (lambda (p a)
      (cond
        [(null? a) '()]
        [else (cons (p (car a)) (map p (cdr a)))])))


; Second Method to implement append
; (define append
;   ( lambda (x y)
;     (cond
;        [(null? x) y ]
;        [else (cons (car x) (append (cdr x) y)) ])))

;8)
 (define append
    ( lambda (x y)
      (cond
        [(null? y) '()]
        [(null? x) (cons (car y) (append x (cdr y))) ]
        [else (cons (car x) (append (cdr x) y)) ])))


;9)
(define reverse
    (lambda (x)
      (cond
        [(null? x) '()]
        [else (append (reverse (cdr x)) (list (car x)))]
        )))

;10)
(define fact
    (lambda (x)
      (cond
        [(zero? x) 1]
        [else (* x (fact (- x 1)))])))

;11)
(define member-?* 
    (lambda (a)
      (cond
        [(null? a) #f]
        [(pair? (car a)) (member-?* (car a))]
        [(eqv? '? (car a)) #t]
        [else (member-?* (cdr a))]
        )))

;12)
(define fib
    (lambda (x)
      (cond
        [(zero? x) 0]
        [(eqv? '1 x) 1]
        [(eqv? '2 x) 1]
        [ else (+ (fib (- x 2)) (fib (- x 1)))]
        )))
;13)
(define cons-cell-count
    (lambda (x)
      (cond
        [(null? x) 0]
        [(pair? x) (add1 (+ (cons-cell-count (car x)) (cons-cell-count (cdr x))))]
        [else 0 ]
        )))

;14) (equal? '((w x) y (z)) '((w . (x . ())) . (y . ((z . ()) . ()))))

;15)

(define binary->natural
    (lambda (x)
      (cond
        [(null? x) 0 ]
        [(eqv? (car x) 1) (+ (* (binary->natural (cdr x)) 2) 1)]
        [(eqv? (car x) 0) (* (binary->natural (cdr x)) 2)])))

;Modulo function to use in below code

 (define %
    (lambda (x y)
    (cond
      [(zero? y) x]
      [(eqv? y 1) 0]
      [(eqv? x 0) 0]
      [(< x y)  x ]
      [else (% (- x y) y)])))

;16)
(define natural->binary
    (lambda (x)
      (cond
        [(zero? x) '(0)]
        [(eqv? '1 x) '(1)]
        [ else (cons (% x 2) (natural->binary (/ (- x (% x 2)) 2)))]
        )))

;17)fold right
;insertR-fr

 (define (insertR-fr x y l)
    (fold-right
      (lambda (l a)
        (cond
          [(eqv? l x) (cons x (cons y a))]
          [else (cons l a)])) '() l))


;occurs-?s-fr

 (define (occurs-?s-fr x)
    (fold-right
      (lambda (x a)
        (cond
          [(eqv? x '?) (+ a 1)]
          [else a])) 0 x))

;filter-fr

 (define (filter-fr p x)
    (fold-right
      (lambda (x a)
        (cond
          [(p x) (cons x a)]
         [else a])) '() x))


;zip-fr

 (define (zip-fr x y)
    (fold-right
      (lambda (x y a)
        (cons (cons x y) a)) '() x y))

;map-fr

(define (map-fr p x)
    (fold-right
      (lambda (x a)
        (cons  (p x) a)) '() x))

;append-fr

 (define (append-fr x y)
    (fold-right
      (lambda (x a)
        (cons x a)) y x))

;reverse-fr

 (define (reverse-fr x)
    (fold-right
      (lambda (x a)
        (append a (list x))) '() x))

;binary->natural-fr
 
(define (binary->natural-fr x)
    (fold-right
      (lambda (x a)
        (cond
          [(eqv? x '1) (+ (* a 2) 1)]
          [(eqv? x '0) (* a 2)]
           )) 0 x))

;18)

 (define fact-acc
    (lambda (x factorial)
      (cond
        [(zero? x) factorial]
        [else (fact-acc (- x 1) (* x factorial))]
        )))


;19)

(define power
    (lambda (x n)
      (cond
        [(zero? n) 1]
        [(odd? n) (* x (power x (- n 1)))]
        [(even? n) (* (power x (/ n 2)) (power x (/ n 2)))]
        )))

;20) Minus

(define -
    (lambda (x y)
      (cond
        [(zero? y) x]
        [else (sub1 (- x (sub1 y)))])))


;Divide

 (define /
    (lambda (x y)
    (cond
      [(zero? y) "ooops! divide by zero leads to infinity"]
      [(eqv? y 1) x]
      [(eqv? x 0) 0]
      [(< x y) 0]
      [else (add1 (/ (- x y) y))])))

;21)

(define base
  (lambda (x)
    (errorf 'error "Invalid value ~s~n" x)))
 
(define odd-case
  (lambda (recur)
    (lambda (x)
      (cond 
        ((odd? x) (collatz (add1 (* x 3)))) 
        (else (recur x))))))
 
(define even-case
  (lambda (recur)
    (lambda (x)
      (cond 
        ((even? x) (collatz (/ x 2))) 
        (else (recur x))))))
 
(define one-case
 (lambda (recur)
   (lambda (x)
     (cond
       ((zero? (sub1 x)) 1)
       (else (recur x))))))

(define collatz
    (lambda (x)
      (cond
        [(eqv? x 1) ((one-case base) x)]
        [(odd? x) ((odd-case base) x)]
        [(even? x) ((even-case base) x)]
        [else (base x)])))


;Concepts of assignments are discussed with ankit khandelwal 
