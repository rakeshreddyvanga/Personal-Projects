 (define empty-k
  (lambda ()
    (let ((once-only #f))
      (lambda (v)
        (if once-only 
	    (errorf 'empty-k "You can only invoke the empty continuation once")
	    (begin (set! once-only #t) v))))))
 
 (define times-cps 
  (trace-lambda timestracecps  (ls k)
    (cond
      [(null? ls) (k 1)]
      [(zero? (car ls)) (k 0)]
      [else (times-cps (cdr ls) (lambda (v) 
                                  (k (* (car ls) v))))])))
								 
(define fib-cps
  (lambda (n k)
    ((lambda (fib-cps k)
      (fib-cps fib-cps n k))
        (lambda (fib-cps n k)
          (cond
          [(zero? n) (k 0)]
          [(= 1 n) (k 1)]
          [(fib-cps fib-cps (sub1 n) (lambda (v) 
                              (fib-cps fib-cps (sub1 (sub1 n)) (lambda (w)
                                                         (k (+ v w))))))])) k)))
								  
 (trace-define M-cps
	(lambda (f k)
		(k (lambda (ls k)
			(cond
				[(null? ls) (k '())]
				[else (M-cps f (lambda (v) 
                           (f (car ls) (lambda (v1)
                                        (v (cdr ls) (lambda (v2)
		                                    (k (cons v1 v2))))))))])))))
											
											