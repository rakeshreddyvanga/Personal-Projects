(load "pmatch.scm")
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
      [(const n) (app-k k n)]
      [(var v) (apply-env env v k)]
      [(if test conseq alt)
       (value-of test env (kon_if-k conseq alt env k))]
      [(mult rand1 rand2) (value-of rand1 env (kon_*-outer-k rand2 env k))]
      [(sub1 rand) (value-of rand env (kon_sub1-k k))]
      [(zero rand) (value-of rand env (kon_zero-k k))]
      [(capture body)
       (value-of body (envr_extend k env) k)]
      [(return vexp kexp)
       (value-of kexp env (kon_return-k vexp env))]
     [(let vexp body) (value-of vexp env (let-k body env k))]
     [(lambda body) (app-k k (clos_closure body env))]
      [(app rator rand) (value-of rator env (kon_app-outer-k rand env k))])))


(define-union envr
  (empty)
  (extend arg env))
 
(define apply-env
  (lambda (env num k)
    (union-case env envr
      [(empty) (errorf 'env "unbound variable")]
      [(extend arg env)
       (if (zero? num)
          (app-k k arg)
     (apply-env env (sub1 num) k))])))
 
(define-union clos
  (closure code env))
 
(define apply-closure
  (lambda (c a k)
    (union-case c clos
      [(closure code env)
       (value-of code (envr_extend a env) k)])))



(define-union kon
  (empty-k)
  (*-inner-k n1 k)
  (*-outer-k x2 env k)
  (sub1-k k)
  (zero-k k)
  (let-k body env k)
  (if-k c a env k)
  (return-k v-exp env)
  (app-inner-k clos k)
  (app-outer-k rand env k))

(define app-k
    (lambda (k v)
      (union-case k kon
        [(empty-k) v]
        [(*-inner-k n1 k) (app-k k (* n1 v))]
        [(*-outer-k x2 env k) (value-of x2 env (kon_*-inner-k v k))]
        [(sub1-k k) (app-k k (- v 1))]
        [(zero-k k) (app-k k (zero? v))]
        [(let-k body env k) (value-of body (envr_extend v env) k)]
        [(if-k c a env k) (if v
                          (value-of c env k)
                          (value-of a env k))]
        [(return-k v-exp env) (value-of v-exp env v)]
        [(app-inner-k clos k) (apply-closure clos v k)]
        [(app-outer-k rand env k) (value-of rand env (kon_app-inner-k v k))])))
