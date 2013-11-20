(load "pmatch.scm")
(load "parenthec.ss")

(define-registers *k *v *expr *env *num *c *a)
(define-program-counter *pc)

 
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

(define-union envr
  (empty)
  (extend arg env))


(define-union clos
  (closure code env))
 
(define-union kon
  (empty-k dismount)
  (*-inner-k n1 k)
  (*-outer-k x2 env k)
  (sub1-k k)
  (zero-k k)
  (let-k body env k)
  (if-k c a env k)
  (return-k v-exp env)
  (app-inner-k clos k)
  (app-outer-k rand env k))

 
(define-label apply-env ;; *env *num *k
    (union-case *env envr
      [(empty) (errorf 'env "unbound variable")]
      [(extend arg env)
       (if (zero? *num)
          (begin
            (set! *k *k)
            (set! *v arg)
            (app-k))
          (begin
            (set! *k *k)
            (set! *env env)
            (set! *num (- *num 1))
            (apply-env)))]))


 
(define-label apply-closure ;; *c *a *k
    (union-case *c clos
      [(closure code env) (begin
                          (set! *k *k)
                          (set! *env (envr_extend *a env))
                          (set! *expr code)
                           (value-of))]))



(define-label app-k ;; *k *v
      (union-case *k kon
        [(empty-k dismount) (dismount-trampoline dismount)]
        [(*-inner-k n1 k) (begin
                            (set! *k k)
                            (set! *v (* n1 *v))
                             (app-k))]
        [(*-outer-k x2 env k) (begin
                              (set! *k (kon_*-inner-k *v k))
                              (set! *env env)
                              (set! *expr x2)
                              (value-of))]
        [(sub1-k k) (begin
                    (set! *k k)
                    (set! *v (- *v 1))
                     (app-k))]
        [(zero-k k) (begin
                      (set! *k k)
                      (set! *v (zero? *v))
                      (app-k))]
        [(if-k c a env k) (if *v
                          (begin
                          (set! *k k)
                          (set! *env env)
                          (set! *expr c)
                          (value-of))
                          (begin
                          (set! *k k)
                          (set! *env env)
                          (set! *expr a)
                          (value-of)))]
        [(let-k body env k) (begin
                            (set! *k k)
                            (set! *env (envr_extend *v env))
                            (set! *expr body)
                            (value-of))]
        [(return-k v-exp env) (begin
                              (set! *k *v)
                              (set! *env env)
                              (set! *expr v-exp)
                              (value-of))]
        [(app-inner-k clos k) (begin
                              (set! *k k)
                              (set! *a *v)
                              (set! *c clos)
                              (apply-closure))]
        [(app-outer-k rand env k) (begin
                                  (set! *k (kon_app-inner-k *v k))
                                  (set! *env env)
                                  (set! *expr rand)
                                  (value-of))]))



 
(define-label value-of ;; *expr *env *k 
    (union-case *expr exp
      [(const n) (begin
                    (set! *k *k)
                    (set! *v n)
                    (app-k))]
      [(var v) (begin
                (set! *k *k)
                (set! *env *env)
                (set! *num v)
                (apply-env))]
      [(if test conseq alt) (begin
                              (set! *k (kon_if-k conseq alt *env *k))
                              (set! *env *env)
                              (set! *expr test)
                             (value-of))]
      [(mult rand1 rand2) (begin
                            (set! *k (kon_*-outer-k rand2 *env *k))
                            (set! *env *env)
                            (set! *expr rand1)
                           (value-of))]
      [(sub1 rand) (begin
                    (set! *k (kon_sub1-k *k))
                    (set! *env *env)
                    (set! *expr rand)
                     (value-of))]
      [(zero rand) (begin
                    (set! *k (kon_zero-k *k))
                    (set! *env *env)
                    (set! *expr rand)
                     (value-of))]
      [(capture body) (begin
                        (set! *k *k)
                        (set! *env (envr_extend *k *env))
                        (set! *expr body)
                          (value-of))]
      [(return vexp kexp) (begin
                            (set! *k (kon_return-k vexp *env))
                            (set! *env *env)
                            (set! *expr kexp)
                            (value-of))]
      [(let vexp body) (begin
                        (set! *k (kon_let-k body *env *k))
                        (set! *env *env)
                        (set! *expr vexp)
                         (value-of))]
      [(lambda body) (begin 
                      (set! *k *k)
                      (set! *v (clos_closure body *env))
                      (app-k))]
      [(app rator rand) (begin
                        (set! *k (kon_app-outer-k rand *env *k))
                        (set! *env *env)
                        (set! *expr rator)
                      (value-of))]))


(define-label main
  (begin
    (set! *env (envr_empty))
    (set! *expr (exp_app
                   (exp_lambda
                          (exp_app
                                  (exp_app (exp_var 0) (exp_var 0))
                                        (exp_const 5)))
                       (exp_lambda
                              (exp_lambda
                                      (exp_if (exp_zero (exp_var 0))
                                              (exp_const 1)
                                                    (exp_mult (exp_var 0)
                                                      (exp_app
                                                         (exp_app (exp_var 1) (exp_var 1))
                                                          (exp_sub1 (exp_var 0)))))))))
    (set! *pc value-of)
    (mount-trampoline kon_empty-k *k *pc)
    (printf "~s\n" *v)
    
    (set! *env (envr_empty))
    (set! *expr (exp_mult (exp_const 2)
                     (exp_capture
                         (exp_mult (exp_const 5)
                           (exp_return (exp_mult (exp_const 2) (exp_const 6))
                               (exp_var 0))))))
    (set! *pc value-of)
    (mount-trampoline kon_empty-k *k *pc)
    (printf "~s\n" *v)


    (set! *env (envr_empty))
    (set! *expr (exp_let
          (exp_lambda
                 (exp_lambda
                        (exp_if (exp_zero (exp_var 0))
                                         (exp_const 1)
                                                 (exp_mult
                                                    (exp_var 0)
                                                        (exp_app
                                                             (exp_app (exp_var 1) (exp_var 1))
                                                                 (exp_sub1 (exp_var 0)))))))
                                                                  (exp_app (exp_app (exp_var 0) (exp_var 0)) (exp_const 5))))
    (set! *pc value-of)
    (mount-trampoline kon_empty-k *k *pc)
    (printf "~s\n" *v)))

