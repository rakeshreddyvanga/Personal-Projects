(optimize-level 3)
(load "test.scm")
(load "minikanren.scm")
(load "mk.scm")
(load "pmatch.scm")

(define reverseo
  (lambda (ls q)
    (conde
      [(nullo ls) (== '() q)]
      [(fresh (ocdr rout cout)
         (cdro ls ocdr)
         (reverseo ocdr rout)
         (caro ls cout)
         (appendo rout `(,cout) q))])))

(define list?
  (lambda (ls)
    (cond
      [(null? ls) #t]
      [(symbol? ls) #f]
      [else (list? (cdr ls))])))

(define listo
  (lambda (ls)
    (conde
      [(nullo ls)]
      [(fresh (lcdr)
        (cdro ls lcdr)
        (listo lcdr))])))

(define fact
    (lambda (x)
      (cond
        [(zero? x) 1]
        [else (* x (fact (- x 1)))])))


(define facto
  (lambda (n out)
    (conde
      [(== n '()) (== out '(1))]
     ;; [(== n '(1)) (== out '(1))]
      [(fresh (res sub1)
        (facto sub1 res)
        (minuso n '(1) sub1)
        (*o n res out)
        )])))

(define fibs
    (lambda (n)
      (cond
        ((eqv? n 0) (values 1 1))
        (else
         (let ((n- (- n 1)))
           (let-values (((u v) (fibs n-)))
             (let ((u+v (+ u v)))
               (values v u+v))))))))


(define fibso
  (lambda (n o1 o2)
    (conde
      [(== n '()) (== o1 '(1)) (== o2 '(1))]
      [(fresh (sb res1 res2 ad)
        (== res2 o1)
        (== ad o2)
        (minuso n '(1) sb)
        (fibso sb res1 res2)
        (pluso res1 res2 ad))])))

(define lookupo
  (lambda (x senv denv out)
    (fresh (y senv^ v denv^)
      (== `(,y . ,senv^) senv)
      (== `(,v . ,denv^) denv)
      (conde
        ((== x y) (== v out))
        ((=/= x y) (lookupo x senv^ denv^ out))))))

(define fo-lavo*o
  (lambda (a* senv denv out)
    (conde ;; a*
      [(== `() a*) (== `() out)]
      [(fresh (a a*^)
         (== `(,a . ,a*^) a*)
         (fresh (v v*^)
           (== `(,v . ,v*^) out)
           (fo-lavo*o a*^ senv denv v*^)
           (fo-lavo a senv denv v)))])))


(define val-of*o
  (lambda (a* senv denv out)
    (conde ;; a*
      [(== `() a*) (== `() out)]
      [(fresh (a a*^)
         (== `(,a . ,a*^) a*)
         (fresh (v v*^)
           (== `(,v . ,v*^) out)
           (val-ofo a senv denv v)
           (val-of*o a*^ senv denv v*^)))])))


(define val-ofo
  (lambda (exp senv denv out)
    (conde ;; exp
;;      [(numbero exp) (== exp out)]
      [(symbolo exp) (lookupo exp senv denv out)]
      [(fresh (v)
         (== `(quote ,v) exp)
         (absento 'closure v)
         (absento 'quote senv)
         (== v out))]
      [(fresh (a*)
         (== `(list . ,a*) exp)
         (absento 'list senv)
         (val-of*o a* senv denv out))]
      [(fresh (x body)
         (== `(lambda (,x) ,body) exp)
         (symbolo x)
         (absento 'lambda senv)
         (== out `(closure ,x ,body ,senv ,denv)))]
      [(fresh (rator rand)
         (== `(,rator ,rand) exp)
         (fresh (x body senv^ denv^ a)
           (val-ofo rator senv denv `(closure ,x ,body ,senv^ ,denv^))
           (val-ofo rand senv denv a)
           (val-ofo body `(,x . ,senv^) `(,a . ,denv^) out)))])))


(define fo-lavo
  (lambda (exp senv denv out)
    (conde ;; exp
;;    [(numbero exp) (== exp out)]
      [(symbolo exp) (lookupo exp senv denv out)]
      [(fresh (v)
         (== `(,v etouq) exp)
         (absento 'closure v)
         (absento 'etouq senv)
         (== v out))]
      [(fresh (a* rexp)
        (reverseo exp rexp)
        (== `(tsil . ,a*) rexp)
        (fo-lavo*o a* senv denv out))]
     ; [(fresh (a* a*^ v* v*^)
      ;   (== `(,a* ,a*^ tsil) exp)
       ;  (absento 'tsil senv)
        ; (== out `(,v*  ,v*^))
       ;  (fo-lavo a* senv denv v*)
       ;  (fo-lavo a*^ senv denv v*^))]
       ;;(fo-lavo*o a* senv denv out))]
      [(fresh (x body)
         (== `(,body (,x) adbmal) exp)
         (symbolo x)
         (absento 'adbmal senv)
         (== out `(closure ,x ,body ,senv ,denv)))]
      [(fresh (rator rand)
         (== `(,rand ,rator) exp)
         (fresh (x body senv^ denv^ a)
           (fo-lavo rator senv denv `(closure ,x ,body ,senv^ ,denv^))
           (fo-lavo rand senv denv a)
           (fo-lavo body `(,x . ,senv^) `(,a . ,denv^) out)))])))


;;---- pratice problem------
(define demo
  (lambda (ls out)
    (conde
      [(fresh (c1 c2 c3)
        (== out `((earth . ,c1) (moon . ,c2) (sun . ,c3)))
        (=/= c1 c2)
        (=/= c2 c3)
        (=/= c3 c1)
        (membero c1 ls)
        (membero c2 ls)
        (membero c3 ls))])))
;;----------------------------

(define color-middle-earth
  (lambda (ls)
    (run 1 (q) (four-color-theorem ls q))))


(define four-color-theorem
  (lambda (ls out)
    (fresh (c1 c2 c3 c4 c5 c6 c7 c8 c9 c0 c10)
       (== out `((lindon . ,c0) (forodwaith . ,c1)
                  (eriador . ,c2) (rhovanion . ,c3) (enedwaith . ,c4)
                  (rohan . ,c5) (gondor . ,c6) (rhun . ,c7)
                  (mordor . ,c8) ( khand . ,c9) (harad . ,c10)))
        (=/= c0 c2) 
        (=/= c0 c1)
        (=/= c1 c3)
        (=/= c1 c2)
        (=/= c2 c3)
        (=/= c2 c4)
        (=/= c3 c4)
        (=/= c3 c5)
        (=/= c3 c7)
        (=/= c4 c5)
        (=/= c4 c6)
        (=/= c5 c7) 
        (=/= c5 c6) 
        (=/= c5 c8)
        (=/= c6 c8)
        (=/= c7 c9) 
        (=/= c7 c8)
        (=/= c8 c9)
        (=/= c8 c10)
        (=/= c9 c10)
        (membero c0 ls)
        (membero c1 ls)
        (membero c2 ls)
        (membero c3 ls)
        (membero c4 ls)
        (membero c5 ls)
        (membero c6 ls)
        (membero c7 ls)
        (membero c8 ls)
        (membero c9 ls)
        (membero c10 ls))))


;;-----------------------------Brain Teaser--------------------------------
 ;;to allow consecutive digits such as 44 (whose value is forty-four)
 ;;the operator I have used is 'join'

(define arithmetic
  (lambda (p r parse result)
    (fresh (q result1)
      (base p q result1)
      (modify result1 result1 q r parse result))))
 
(define base
  (lambda (p r result)
    (== p `(,result . ,r))))
 
(define modify
  (lambda (parse1 result1 p r parse result)
    (conde [(== p r)
            (== parse1 parse)
            (== result1 result)]
           [(fresh (q parse2 result2 parse0 result0)
              (arithmetic p q parse2 result2)
              (conde [(== parse0 `(+ ,parse1 ,parse2))
                      (pluso result1 result2 result0)]
                     [(== parse0 `(- ,parse1 ,parse2))
                      (pluso result2 result0 result1)]
                     [(== parse0 `(* ,parse1 ,parse2))
                      (*o result1 result2 result0)]
                     [(== parse0 `(join ,parse1 ,parse2))
                        (consec result1 result2 result0)]
                     [(== parse0 `(+ (sqrt ,parse1) ,parse2))
                          (== parse1 '(0 0 1))
                          (pluso '(0 1) result2 result0)]
                     [(== parse0 `(+ ,parse1 (sqrt ,parse2)))
                          (== parse2 '(0 0 1))
                          (pluso result1 '(0 1) result0)]
                     [(== parse0 `(- ,parse1 (sqrt ,parse2)))
                          (== parse2 '(0 0 1))
                          (pluso result0 '(0 1) result1)]
                     [(== parse0 `(- (sqrt ,parse1) ,parse2))
                          (== parse1 '(0 0 1))
                          (pluso result2 result0 '(0 1))]
                     [(== parse0 `(* (sqrt ,parse1) ,parse2))
                          (== parse1 '(0 0 1))
                          (*o '(0 1) result2 result0)]
                     [(== parse0 `(* ,parse1 (sqrt ,parse2)))
                          (== parse2 '(0 0 1))
                          (*o result1 '(0 1) result0)]
                     [(== parse0 `(/ (sqrt ,parse1) ,parse2))
                          (== parse1 '(0 0 1))
                          (poso result2)
                          (*o result2 result0 '(0 1))]
                     [(== parse0 `(/ ,parse1 (sqrt ,parse2)))
                          (== parse2 '(0 0 1))
                          (poso result2)
                          (*o '(0 1) result0 result1)]
                     [(== parse0 `(/ ,parse1 ,parse2))
                      (poso result2)
                      (*o result2 result0 result1)])
              (modify parse0 result0 q r parse result))])))
 
(define four-fours
  (let ((p (map build-num '(4 4 4 4))))
    (lambda (n)
      (run 1 (parse)
        (arithmetic p '() parse (build-num n))))))

(define consec
  (lambda (result1 result2 result3)
    (fresh (mul)
      (== result1 result2)
      (*o '(0 1 0 1) result1 mul)
      (pluso mul result2 result3))))

;(define square
;  (lambda (result out)
 ;     (== result '(0 0 1))
  ;    (== out '(0 1))))


;;(define do-consec
;;  (lambda (result1 result2 result3)
;;    (run 1 (result) (consec result1 result2 result)
;;                    (== result result3))))
