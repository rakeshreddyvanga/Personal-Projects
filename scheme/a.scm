                   ;                                                  |
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


