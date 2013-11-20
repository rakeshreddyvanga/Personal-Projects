
(load "parenthec.ss")
 
(define-union exp
  (const n)
  (var v)
  (if test conseq alt)
  (mult rand1 rand2)
  (sub1 rand)
  (zero rand)
  (capture body)
  (return vexp kexp)
  (let vexp body)
  (lambda body)
  (app rator rand))
 
(define value-of
  (lambda (expr env k)
    (union-case expr exp
      [(const n) (app-k-fn k n)]
      [(var v) (apply-env env v k)]
      [(if test conseq alt)
       (value-of test env (if-k-fn conseq alt env k))]
      [(mult rand1 rand2) (value-of rand1 env (*-outer-k-fn rand2 env k))]
      [(sub1 rand) (value-of rand env (sub1-k-fn k))]
      [(zero rand) (value-of rand env (zero-k-fn k))]
      [(capture body)
       (value-of body (envr_extend k env) k)]
      [(return vexp kexp)
       (value-of kexp env (return-k-fn vexp env))]
      [(let vexp body) (value-of vexp env (let-k-fn body env k))]
      [(lambda body) (app-k-fn k (clos_closure body env))]
      [(app rator rand)
        (value-of rator env (app-outer-k-fn rand env k))])))

(define let-k-fn
  (lambda (body env k)
    (lambda (v)
      (value-of body (envr_extend v env) k))))
 
(define app-inner-k-fn
  (lambda (clos k)
    (lambda (a)
      (apply-closure clos a k))))

(define app-outer-k-fn
  (lambda (rand env k)
    (lambda (clos)
      (value-of rand env (app-inner-k-fn clos k)))))

(define return-k-fn
  (lambda (vexp env)
    (lambda (k^)
      (value-of vexp env k^))))

(define sub1-k-fn
  (lambda (k)
    (lambda (n)
      (app-k-fn k (- n 1)))))

(define zero-k-fn
  (lambda (k)
    (lambda (n)
      (app-k-fn k (zero? n)))))

(define *-inner-k-fn
  (lambda (n1 k)
    (lambda (n2)
      (app-k-fn k (* n1 n2)))))

(define *-outer-k-fn
  (lambda (rand2 env k)
    (lambda (n1)
      (value-of rand2 env (*-inner-k-fn n1 k)))))

(define if-k-fn
  (lambda (conseq alt env k)
    (lambda (t)
      (if t
        (value-of conseq env k)
        (value-of alt env k)))))


(define app-k-fn
  (lambda (k v)
    (k v)))

(define empty-k-fn
  (lambda ()
    (lambda (v) v)))




(define-union envr
  (empty)
  (extend arg env))
 
(define apply-env
  (lambda (env num k)
    (union-case env envr
      [(empty) (errorf 'env "unbound variable")]
      [(extend arg env)
       (if (zero? num)
          (app-k-fn k arg)
     (apply-env env (sub1 num) k))])))
 
(define-union clos
  (closure code env))
 
(define apply-closure
  (lambda (c a k)
    (union-case c clos
      [(closure code env)
       (value-of code (envr_extend a env) k)])))

