(load "test.scm")
(load "mk.scm")
(load "minikanren.scm")

;; Part I 
;; Write the answers to the following problems using your
;; knowledge of miniKanren.  For each problem, explain how miniKanren
;; arrived at the answer.  You will be graded on the quality of your
;; explanation; a full explanation will require several sentences.

;; 1 What is the value of 
(run 2 (q)
  (== 5 q)
  (conde
    [(conde 
        [(== 5 q) (== 6 q)]) (== 5 q)]
    [(== q 5)]))
;; The inner conde will not succeed because the expression (== 6 q) is unsucessfull. As q is already set to 5, the expression (== 6 q) is #u. 
;; So the first goal of the outer conde will not succeed. 
;; Since "q" is set to 5 in the second step, the second goal of the outer conde succeeds and the run results in (5).
;; "q" has only one value so run*, run 1 and run 2 result the same output.
;;
;;



;; 2 What is the value of
(run 1 (q) 
  (fresh (a b) 
    (== `(,a ,b) q)
    (absento 'tag q)
    (symbolo a)))
;;(((_.0 _.1)
;;   (=/= ((_.0 tag)))
;;   (sym _.0)
;;   (absento (tag _.1))))
;; This because we are creating fresh values a b and associating the list which contains a and b to q so the step (_.0 _.1). Here _.0 is the 
;; first element in the ouput list and _.1 is the second element and to show that these two are different different numbers are given in the tag
;; names.
;; We are also explicitly stating that q should not contain "tag" so the conditions (=/= ((_.0 tag))) & (absento (tag _.1)) in the output.
;; Since we know that a is symbol so we got the output as "a" =/= tag and "b" can be list, so the output says that "b" should not "tag".
;; And also we stated that "a" should be a symbol so (sym _.0). Here run* and run 1 both give the same output because q has only one value 
;; associated to it. 

;; part II goes here.

;;--------------Helper functions-----------------
(define appendo
  (lambda (l s out)
    (conde
      ((nullo l) (== s out))
      ((fresh (a d res)
         (conso a d l)
         (conso a res out)
         (appendo d s res))))))

(define caro
  (lambda (p a)
    (fresh (d)
      (== (cons a d) p))))

(define cdro
  (lambda (p d)
    (fresh (a)
      (== (cons a d) p))))

(define conso
  (lambda (a d p)
    (== (cons a d) p)))

(define nullo
  (lambda (x)
    (== '() x)))
;;--------------------------------------------------


(define one-item
  (lambda (x s)
    (cond
      [(null? s) '()]
      [else (cons (cons x (car s))
              (one-item x (cdr s)))])))

(define one-itemo
  (lambda (x s o)
    (conde
      [(== '() s) (== '() o)]
      [(fresh (icons icar res rcdr)
         (caro s icar)
         (cdro s rcdr)
         (conso x icar icons)
         (conso icons res o)
         (one-itemo x rcdr res))])))

(define assoco
  (lambda (x ls o)
    (conde
      [(fresh (icar)
        (caro ls icar)
        (caro icar x)) (caro ls o)]
      [(fresh (ocdr)
        (cdro ls ocdr)
        (assoco x ocdr o))])))

(define reverseo
  (lambda (ls q)
    (conde
      [(nullo ls) (== '() q)]
      [(fresh (ocdr rout cout)
         (cdro ls ocdr)
         (reverseo ocdr rout)
         (caro ls cout)
         (appendo rout `(,cout) q))])))

(define lengtho
  (lambda (ls q)
    (conde
      [(nullo ls) (== '() q)]
      [(fresh (ocdr res)
        (cdro ls ocdr)
        (lengtho ocdr res)
        (addero 0 '(1) res q))])))
