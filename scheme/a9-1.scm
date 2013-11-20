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
      [(const n) (k n)]
      [(var v) (apply-env env v k)]
      [(if test conseq alt)
       (value-of test env (lambda (t)
        (if t
     (value-of conseq env k)
     (value-of alt env k))))]
      [(mult rand1 rand2) (value-of rand1 env (lambda (n1)  (value-of rand2 env (lambda (n2) (k (* n1 n2))))))]
      [(sub1 rand) (value-of rand env (lambda (n) (k (- n 1))))]
      [(zero rand) (value-of rand env (lambda (n) (k (zero? n))))]
      [(capture body)
       (value-of body (envr_extend k env) k)]
      [(return vexp kexp)
       (value-of kexp env (lambda (k^) (value-of vexp env k^)))]
      [(let vexp body) (value-of vexp env (lambda (v) (value-of body (envr_extend v env) k)))]
      [(lambda body) (k (clos_closure body env))]
      [(app rator rand)
        (value-of rator env (lambda (clos) (value-of rand env (lambda (a) (apply-closure clos a k)))))])))
 
(define-union envr
  (empty)
  (extend arg env))
 
(define apply-env
  (lambda (env num k)
    (union-case env envr
      [(empty) (errorf 'env "unbound variable")]
      [(extend arg env)
       (if (zero? num)
          (k arg)
     (apply-env env (sub1 num) k))])))
 
(define-union clos
  (closure code env))
 
(define apply-closure
  (lambda (c a k)
    (union-case c clos
      [(closure code env)
       (value-of code (envr_extend a env) k)])))
