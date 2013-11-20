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
      [(const n) (app-k-ds k n)]
      [(var v) (apply-env env v k)]
      [(if test conseq alt)
       (value-of test env (if-k-ds conseq alt env k))]
      [(mult rand1 rand2) (value-of rand1 env (*-outer-k-ds rand2 env k))]
      [(sub1 rand) (value-of rand env (sub1-k-ds k))]
      [(zero rand) (value-of rand env (zero-k-ds k))]
      [(capture body)
       (value-of body (envr_extend k env) k)]
      [(return vexp kexp)
       (value-of kexp env (return-k-ds vexp env))]
      [(let vexp body) (value-of vexp env (let-k-ds body env k))]
      [(lambda body) (app-k-ds k (clos_closure body env))]
      [(app rator rand) (value-of rator env (app-outer-k-ds rand env k))])))

(define let-k-ds
  (lambda (body env k)
    `(let-k-ds ,body ,env ,k)))



 (define *-inner-k-ds
    (lambda (n1 k)
      `(*-inner-k-ds ,n1 ,k)))

  (define *-outer-k-ds
    (lambda (x2 env k)
      `(*-outer-k-ds ,x2 ,env ,k)))

(define app-k-ds
    (lambda (k v)
      (pmatch-who "app-k" k
        [`(empty-k-ds) v]
        [`(*-inner-k-ds ,n1 ,k) (app-k-ds k (* n1 v))]
        [`(*-outer-k-ds ,x2 ,env ,k) (value-of x2 env (*-inner-k-ds v k))]
        [`(sub1-k-ds ,k) (app-k-ds k (- v 1))]
        [`(zero-k-ds ,k) (app-k-ds k (zero? v))]
        [`(let-k-ds ,body ,env ,k) (value-of body (envr_extend v env) k)]
        [`(if-k-ds ,c ,a ,env ,k) (if v
                                      (value-of c env k)
                                      (value-of a env k))]
        [`(return-k-ds ,v-exp ,env) (value-of v-exp env v)]
        [`(app-inner-k-ds ,clos ,k) (apply-closure clos v k)]
        [`(app-outer-k-ds ,rand ,env ,k) (value-of rand env (app-inner-k-ds v k))])))

  (define sub1-k-ds
    (lambda (k)
      `(sub1-k-ds ,k)))

    (define zero-k-ds
        (lambda (k)
          `(zero-k-ds ,k)))

      (define if-k-ds
          (lambda (conseq alt env k)
            `(if-k-ds ,conseq ,alt ,env ,k)))

        (define return-k-ds
            (lambda (v-exp env)
              `(return-k-ds ,v-exp ,env)))

          (define app-inner-k-ds
              (lambda (clos k)
                `(app-inner-k-ds ,clos ,k)))

            (define app-outer-k-ds
                (lambda (rand env k)
                     `(app-outer-k-ds ,rand ,env ,k)))

(define empty-k-ds
  (lambda ()
    `(empty-k-ds)))



(define-union envr
  (empty)
  (extend arg env))
 
(define apply-env
  (lambda (env num k)
    (union-case env envr
      [(empty) (errorf 'env "unbound variable")]
      [(extend arg env)
       (if (zero? num)
          (app-k-ds k arg)
     (apply-env env (sub1 num) k))])))
 
(define-union clos
  (closure code env))
 
(define apply-closure
  (lambda (c a k)
    (union-case c clos
      [(closure code env)
       (value-of code (envr_extend a env) k)])))

