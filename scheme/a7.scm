(load "pmatch.scm")
(load "test.scm")

(define empty-k
  (lambda ()
    (let ((once-only #f))
      (lambda (v)
        (if once-only 
      (errorf 'empty-k "You can only invoke the empty continuation once")
      (begin (set! once-only #t) v))))))

(define empty-k-fn
 (lambda ()
  (let ((once-only #f))
   (lambda (v)
    (if once-only 
     (errorf 'empty-k-fn "You can only invoke the empty continuation once")
      (begin (set! once-only #t) v))))))

(define binary-to-decimal
 (trace-lambda main (n)
  (cond
   [(null? n) 0]
   [else (+ (car n) (* 2 (binary-to-decimal (cdr n))))])))

(define binary-to-decimal-cps
  (trace-lambda main (n k)
    (cond
      [(null? n) (k 0)]
      [else (binary-to-decimal-cps (cdr n) (lambda (v) 
                                            (k (+ (car n) (* 2 v)))))])))

(define rember*1
 (trace-lambda main (ls)
  (cond
   [(null? ls) '()]
   [(pair? (car ls))
     (cond
       [(equal? (car ls) (rember*1 (car ls)))
           (cons (car ls) (rember*1 (cdr ls)))]
       [else (cons (rember*1 (car ls)) (cdr ls))])]
   [(eq? (car ls) '?) (cdr ls)]
   [else (cons (car ls) (rember*1 (cdr ls)))])))

(trace-define rember*1-cps
  (lambda  (ls k)
    (cond
      [(null? ls) (k '())]
      [(pair? (car ls)) 
                        (cond
                           ;; [(equal? (car ls) (rember*1-cps (car ls)))
                          [(rember*1-cps (car ls) (lambda (v) (equal? (car ls) v)))
                              (rember*1-cps (cdr ls) (lambda  (v) (k (cons (car ls) v))))]
                          [else (rember*1-cps (car ls) (lambda (v) (k (cons v (cdr ls)))))])]
     [(eq? (car ls) '?) (k (cdr ls))]
     [else (rember*1-cps (cdr ls) (lambda (v) (k (cons (car ls) v))))])))


  (define apply-closure
    (lambda (clos v k)
      (clos v k)))


    (define extend-env
      (lambda (id env a)
        (lambda (y k^^)
          (if (eqv? y id)
              (k^^ a)
              (apply-env-ds env y k^^)))))

    (define closure
      (lambda (id body env)
        (lambda (a k^)
          (value-of-cps body (extend-env id env a) k^))))

    (define apply-env
      (lambda (env x k)
        (env x k)))

 (define value-of-cps
 (lambda (expr env k)
   (pmatch expr
     [`,n (guard (or (number? n) (boolean? n))) (k n)]
     [`(+ ,x1 ,x2) (value-of-cps x1 env (lambda (n1) (value-of-cps x2 env (lambda (n2) (k (+ n1 n2))))))]
     [`(* ,x1 ,x2) (value-of-cps x1 env (lambda (n1) (value-of-cps x2 env (lambda (n2) (k (* n1 n2))))))]
     [`(sub1 ,x) (value-of-cps x env (lambda (n) (k (- n 1))))]
     [`(zero? ,x) (value-of-cps x env (lambda (n) (k (zero? n))))]
     [`(if ,test ,conseq ,alt) (value-of-cps test env (lambda (t) (if t
                                                                    (value-of-cps conseq env k)
                                                                    (value-of-cps alt env k))))]
     [`(capture ,k-id ,body) (value-of-cps body (extend-env k-id env k) k)]
     [`(return ,v-exp ,k-exp) (value-of-cps k-exp env (lambda (v) (value-of-cps v-exp env v)))]
     [`,x (guard (symbol? x)) (apply-env env x k)]
     [`(lambda (,id) ,body) (k (closure id body env))]
     [`(,rator ,rand) (value-of-cps rator env (lambda (clos) (value-of-cps rand env (lambda (a) (clos a k)))))])))

(define empty-env
  (lambda ()
    (lambda (y k) 
      (errorf 'value-of "unbound variable ~s" y))))

(define app-k-fn
  (lambda (k v)
    (k v)))

(define plus-inner-k-fn
  (lambda (n1 k)
    (lambda (v)
      (app-k-fn k (+ n1 v)))))

(define plus-outer-k-fn
  (lambda (x2 env k)
    (lambda (v)
      (value-of-cps-fn x2 env (plus-inner-k-fn v k)))))



(define empty-k
 (lambda ()
  (let ((once-only #f))
   (lambda (v)
    (if once-only 
     (errorf 'empty-k "You can only invoke the empty continuation once")
      (begin (set! once-only #t) v))))))


(define *-inner-k-fn
  (lambda (n1 k)
    (lambda (v)
      (app-k-fn k (* n1 v)))))

(define *-outer-k-fn
  (lambda (x2 env k)
    (lambda (v)
      (value-of-cps-fn x2 env (*-inner-k-fn v k)))))

 (define sub1-k-fn
  (lambda (k)
   (lambda (v)
      (app-k-fn k (- v 1)))))

  (define zero-k-fn
    (lambda (k)
      (lambda (v)
        (app-k-fn k (zero? v)))))

  (define if-k-fn
    (lambda (conseq alt env k)
     (lambda (v) 
      (if v
      (value-of-cps-fn conseq env k)
      (value-of-cps-fn alt env k)))))

  (define return-k-fn
    (lambda (v-exp env)
      (lambda (v)
        (value-of-cps-fn v-exp env v))))

  (define app-inner-k-fn
    (lambda (clos k)
      (lambda (v)
        (apply-closure-fn clos v k))))

  (define apply-closure-fn
    (lambda (clos v k)
      (clos v k)))

  (define app-outer-k-fn
    (lambda (rand env k)
      (lambda (v)
        (value-of-cps-fn rand env (app-inner-k-fn v k))))) 

    (define extend-env-fn
      (lambda (id env a)
        (lambda (y k^^)
          (if (eqv? y id)
              (app-k-fn k^^ a)
              (apply-env-fn env y k^^)))))

    (define closure-fn
      (lambda (id body env)
        (lambda (a k^)
          (value-of-cps-fn body (extend-env-fn id env a) k^))))

    (define apply-env-fn
      (lambda (env x k)
        (env x k)))

(define value-of-cps-fn
 (lambda (expr env k)
   (pmatch expr
     [`,n (guard (or (number? n) (boolean? n))) (app-k-fn k n)]
     [`(+ ,x1 ,x2) (value-of-cps-fn x1 env (plus-outer-k-fn x2 env k))]
     [`(* ,x1 ,x2) (value-of-cps-fn x1 env (*-outer-k-fn x2 env k))]
     [`(sub1 ,x) (value-of-cps-fn x env (sub1-k-fn k))]
     [`(zero? ,x) (value-of-cps-fn x env (zero-k-fn k))]
     [`(if ,test ,conseq ,alt) (value-of-cps-fn test env (if-k-fn conseq alt env k))]
     [`(capture ,k-id ,body) (value-of-cps-fn body (extend-env-fn k-id env k) k)]
     [`(return ,v-exp ,k-exp) (value-of-cps-fn k-exp env (return-k-fn v-exp env))]
     [`,x (guard (symbol? x)) (apply-env-fn env x k)]
     [`(lambda (,id) ,body) (app-k-fn k (closure-fn id body env))]
     [`(,rator ,rand) (value-of-cps-fn rator env (app-outer-k-fn rand env k))])))

 
  (define apply-closure-ds
    (lambda (clos v k)
      (clos v k)))


    (define extend-env-ds
      (lambda (id env a)
        (lambda (y k^^)
          (if (eqv? y id)
              (app-k-ds k^^ a)
              (apply-env-ds env y k^^)))))

    (define closure-ds
      (lambda (id body env)
        (lambda (a k^)
          (value-of-cps-ds body (extend-env-ds id env a) k^))))

    (define apply-env-ds
      (lambda (env x k)
        (env x k)))

 (define value-of-cps-ds
 (lambda (expr env k)
   (pmatch expr
     [`,n (guard (or (number? n) (boolean? n))) (app-k-ds k n)]
     [`(+ ,x1 ,x2) (value-of-cps-ds x1 env (plus-outer-k-ds x2 env k))]
     [`(* ,x1 ,x2) (value-of-cps-ds x1 env (*-outer-k-ds x2 env k))]
     [`(sub1 ,x) (value-of-cps-ds x env (sub1-k-ds k))]
     [`(zero? ,x) (value-of-cps-ds x env (zero-k-ds k))]
     [`(if ,test ,conseq ,alt) (value-of-cps-ds test env (if-k-ds conseq alt env k))]
     [`(capture ,k-id ,body) (value-of-cps-ds body (extend-env-ds k-id env k) k)]
     [`(return ,v-exp ,k-exp) (value-of-cps-ds k-exp env (return-k-ds v-exp env))]
     [`,x (guard (symbol? x)) (apply-env-ds env x k)]
     [`(lambda (,id) ,body) (app-k-ds k (closure-ds id body env))]
     [`(,rator ,rand) (value-of-cps-ds rator env (app-outer-k-ds rand env k))]))) 


 ;;  (define apply-closure-ds
  ;  (lambda (clos a k)
   ;  (pmatch-who "apply-closure-ds" clos
    ;     [`(closure-ds ,id ,body ,env) (value-of-cps-ds body (extend-env-ds id env a) k)])))
    
; (define closure-ds
 ;   (lambda (x body env)
  ;    `(closure-ds ,id ,body ,env)))


   ; (define extend-env-ds
    ;  (lambda (k-id env a)
     ;   `((,k-id . ,a) . ,env)))

; (define apply-env-ds
; (lambda (env y k)
;  (pmatch-who "apply-env-data-struct" env
 ; [`() (errorf 'value-of-ds "unbound variable ~s" y)]
 ; [`((,k-id . ,a) . ,env) ((if (eqv? y k-id) (app-k-ds k^ k) (apply-env-ds env y k^)))])))           



 (define plus-inner-k-ds
    (lambda (n1 k)
      `(plus-inner-k-ds ,n1 ,k)))

  (define plus-outer-k-ds
    (lambda (x2 env k)
      `(plus-outer-k-ds ,x2 ,env ,k)))


 (define *-inner-k-ds
    (lambda (n1 k)
      `(*-inner-k-ds ,n1 ,k)))

  (define *-outer-k-ds
    (lambda (x2 env k)
      `(*-outer-k-ds ,x2 ,env ,k)))

(define app-k-ds
    (lambda (k v)
      (pmatch k
        [`(empty-k-ds) v]
        [`(plus-inner-k-ds ,n1 ,k) (app-k-ds k (+ n1 v))]
        [`(plus-outer-k-ds ,x2 ,env ,k) (value-of-cps-ds x2 env (plus-inner-k-ds v k))]
        [`(*-inner-k-ds ,n1 ,k) (app-k-ds k (* n1 v))]
        [`(*-outer-k-ds ,x2 ,env ,k) (value-of-cps-ds x2 env (*-inner-k-ds v k))]
        [`(sub1-k-ds ,k) (app-k-ds k (- v 1))]
        [`(zero-k-ds ,k) (app-k-ds k (zero? v))]
        [`(if-k-ds ,c ,a ,env ,k) (if v
                                      (value-of-cps-ds c env k)
                                      (value-of-cps-ds a env k))]
        [`(return-k-ds ,v-exp ,env) (value-of-cps-ds v-exp env v)]
        [`(app-inner-k-ds ,clos ,k) (apply-closure-ds clos v k)]
        [`(app-outer-k-ds ,rand ,env ,k) (value-of-cps-ds rand env (app-inner-k-ds v k))]
        [else (k v)])))

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


            (define-syntax cons$
              (syntax-rules ()
                  ((cons$ x y) (cons x (delay y)))))
             
             (define car$ car)
              
              (define cdr$
                (lambda ($) (force (cdr $))))

              (define take$
               (lambda (n $)
                (cond
                 ((zero? n) '())
                  (else (cons (car$ $) (take$ (sub1 n) (cdr$ $)))))))

             ; (define trib$
              ;  (cons$ 0 (cons$ 1 (cons$ 1 trib$))))
                  
    (define trib$
      (cons$ 0 (cons$ 1 (cons$ 1 (ad trib$ (cdr$ trib$) (cdr$ (cdr$ trib$)))))))

    (define ad
      (lambda (a b c)
        (cons$ (+ (+ (car$ a) (car$ b)) (car$ c)) (ad (cdr$ a) (cdr$ b) (cdr$ c))))) 

              (define inf-1s
                (cons$ 1 inf-1s))
