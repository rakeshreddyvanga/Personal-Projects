; | NAME: Rakesh Reddy Vanga                                                                     |
; +---------------------------------------------------------------------------+

; +---------------------------------------------------------------------------+
; | LOADS                                                                     |
; +---------------------------------------------------------------------------+
(load "mk.scm")
(load "test.scm")
; +---------------------------------------------------------------------------+
; | TYPE INFERENCER                                                           |
; +---------------------------------------------------------------------------+

(define !-o
  (lambda (G exp t)
    (conde
      [(numbero exp) (== 'nat t)]
      [(booleano exp) (== 'bool t)]
      [(fresh (ne1 ne2)
        (== `(* ,ne1 ,ne2) exp)
        (== 'nat t)
        (!-o G ne1 'nat)
        (!-o G ne2 'nat))]
      [(fresh (ne tx)
        (== `(not ,ne) exp)
        (==  'bool t)
        (!-o G ne tx))]
      [(fresh (test c a t1 t2)
        (== `(if ,test ,c ,a) exp)
        (== t1 t2) 
        (== t1 t)
        (!-o G test 'bool)
        (!-o G c t1)
        (!-o G a t2))]
      [(fresh (ne1 ne2)
        (== `(+ ,ne1 ,ne2) exp)
        (== 'nat t)
        (!-o G ne1 'nat)
        (!-o G ne2 'nat))]
      [(fresh (ne)
        (== `(sub1 ,ne) exp)
        (== 'nat t)
        (!-o G ne 'nat))]
      [(fresh (ne)
        (== `(zero? ,ne) exp)
        (== 'bool t)
        (!-o G ne 'nat))]
      [(fresh (ta td a d)
        (== `(cons ,a ,d) exp)
        (== `(pairof ,ta ,td) t)
        (!-o G a ta)
        (!-o G d td))]
      [(fresh (ta td ls tx)
        (== `(car ,ls) exp)
        (== ta t)
        (== `(pairof ,ta ,td) tx)
        (!-o G ls tx))]
      [(fresh (ta td ls tx)
        (== `(cdr ,ls) exp)
        (== td t)
        (== `(pairof ,ta ,td) tx)
        (!-o G ls tx))]
      [(symbolo exp) (lookupo G exp t)] ;; variable 
      [(fresh (x body tx tbody)
         (not-in-envo 'lambda G)
         (symbolo x)
         (== `(lambda (,x) ,body) exp)
         (== `(-> ,tx ,tbody) t)
         (!-o `((,x . ,tx) . ,G) body tbody))]
      [(fresh (rator rand tx)
         (== `(,rator ,rand) exp)
         (!-o G rator `(-> ,tx ,t))
         (!-o G rand tx))]
      [(fresh (f func x)
         (== `(fix (lambda (,f) ,func)) exp)
         (not-in-envo 'fix G)
         (!-o `((,f . ,t) . ,G) func t))])))

(define not-in-envo
  (lambda (x env)
    (conde
      ((== '() env))
      ((fresh (y __ rest)
         (== `((,y . ,__) . ,rest) env)
         (=/= y x)
         (not-in-envo x rest))))))

(define lookupo
  (lambda (G x t)
    (fresh (rest type y)
      (conde 
      ((== `((,x . ,t) . ,rest) G))
  ((== `((,y . ,type) . ,rest) G)
   (=/= x y)
   (lookupo rest x t))))))

; +---------------------------------------------------------------------------+
; | TYPE INFERENCER TESTS                                                     |
; +---------------------------------------------------------------------------+

(test "1"
  (run* (q) (!-o '() 17 q))
  '(nat))

(test "2"
  (run* (q) (!-o '() '(zero? 24) q))
  '(bool))

(test "3"
  (run* (q) (!-o '() '(zero? (sub1 24)) q))
  '(bool))

(test "4"
  (run* (q)
    (!-o '() '(zero? (sub1 (sub1 18))) q))
  '(bool))

(test "5"
  (run* (q)
    (!-o '()  '(lambda (n) (if (zero? n) n n)) q))
  '((-> nat nat)))

(test "6"
  (run* (q)
    (!-o '() '((lambda (n) (zero? n)) 5) q))
  '(bool))

(test "7"
  (run* (q)
    (!-o '() '(if (zero? 24) 3 4) q))
  '(nat))

(test "8"
  (run* (q)
    (!-o '() '(if (zero? 24) (zero? 3) (zero? 4)) q))
  '(bool))

(test "9"
  (run* (q)
    (!-o '() '(lambda (x) (sub1 x)) q))
  '((-> nat nat)))

(test "10"
  (run* (q)
    (!-o '() '(lambda (a) (lambda (x) (+ a x))) q))
  '((-> nat (-> nat nat))))

(test "11"
  (run* (q)
    (!-o '() '(lambda (f)
                       (lambda (x)
                         ((f x) x)))
         q))
  '((->
     (-> _.0 (-> _.0 _.1))
     (-> _.0 _.1))))

(test "12"
  (run* (q)
    (!-o '() '(sub1 (sub1 (sub1 6))) q))
  '(nat))

(test "13"
  (run 1 (q)
    (fresh (t)
      (!-o '() '(lambda (f) (f f)) t)))
  '())

(test "14"
  (let ([v (run 20 (q)
             (fresh (lam a b)
               (!-o '() `((,lam (,a) ,b) 5) 'nat)
               (== `(,lam (,a) ,b) q)))])
    (pretty-print v)
    (length v))
  20)

(test "15"
  (let ([v (run 30 (q)
             (!-o '() q 'nat))])
    (pretty-print v)
    (length v))
  30)

(test "16"
  (let ([v (run 30 (q)
             (!-o '() q '(-> nat nat)))])
    (pretty-print v)
    (length v))
  30)

(test "17"
  (let ([v (run 30 (q)
             (!-o '() q '(-> bool nat)))])
    (pretty-print v)
    (length v))
  30)

(test "18"
  (let ([v (run 30 (q)
             (!-o '() q '(-> nat (-> nat nat))))])
    (pretty-print v)
    (length v))
  30)

(test "19"
  (let ([v (run 100 (q)
             (fresh (e t)
               (!-o '() e t)
               (== `(,e ,t) q)))])
    (pretty-print v)
    (length v))
  100)

(test "20"
  (let ([v (run 100 (q)
             (fresh (g e t)
               (!-o g e t)
               (== `(,g ,e ,t) q)))])
    (pretty-print v)
    (length v))
  100)

(test "21"
  (length
   (run 100 (q)
     (fresh (g v)
       (!-o g `(var ,v) 'nat)
       (== `(,g ,v) q))))
  100)

;; As we noted in lecture, the simply-typed lambda calculus is strongly-normalizing.
;; From this, it followed that types cannot be found for fixed-point combinators such as Omega.
;; However, you can explicitly add recursion to our language
;;     by including a special operator ''fix'' in your language.
;; After doing so, you should be able to pass the following tests below.

 (define fix
   (lambda (f)
     (letrec ([g (lambda (x)
      ((f g) x))])
       g)))

(test "22"
  (run 1 (q)
       (fresh (g)
   (!-o g
        '((fix (lambda (!)
           (lambda (n)
       (if (zero? n)
           1
           (* n (! (sub1 n)))))))
    5)
        q)))
  '(nat))

;; The following test demonstrates an interesting property:
;; just because a program typechecks doesn't mean it will terminate.

(test "23"
  (run 1 (q)
       (fresh (g)
   (!-o g
        '((fix (lambda (!)
           (lambda (n)
       (* n (! (sub1 n))))))
    5)
        q)))
  '(nat))
