;; INITIALIZATION
;;

CLR 0
SWDD 0 #504 ; result 1
SWDD 0 #506 ; restlt 2
SWDD 0 #508 ; neg flag 1
SWDD 0 #510 ; neg flag 2
SWDD 0 #512 ; one multiplication 1
SWDD 0 #514 ; one multiplication 2
SWDD 0 #516 ; which leftshift flag (#512 or #514)

;; determine negative flags

;; first number
LWDD 0 #500
BZD [end] ; if 0, goto end
SLL ; negative if carry
BCD [first number negative]
BD  [first number positive]

[first number negative]
LWDD 0 #500
NEG
INC
SWDD 0 #500
CLR 0
INC
SWDD 0 #508 ; write 1 to #508

[first number positive]
; check the second number
LWDD 0 #502
SLL
BCD [second number negative]
BD  [second number positive]

[second number negative]
LWDD 0 #502
BZD [end] ; if 0, goto end
NEG
INC
SWDD 0 #502
CLR 0
INC
SWDD 0 #510 ; write 1 to #510

[second number positive]
;; calculate negation
LWDD 0 #508
LWDD 1 #510
OR 1
BNZD [one or both negative]
BD [none negative]

[one or both negative]
;; calculate negation
LWDD 0 #508
AND 1 ; if both are neg => 1 then 0;
      ; if one is neg => 0, then 1
NOT
SWDD 0 #508

[none negative]
;; everything prepared, start real work
LWDD 0 #500
SWDD 0 #512 ; store first number into calculation memory

[mainloop second rightshift]
    LWDD 0 #502
    BRD [end] ; if second is 0 => goto end
    SRL ; right shift. for every rightshift one leftshift
        ; on #512 or #514 if not carry is set
    BCD [rightshift carry]
    BD  [no rightshift carry]

    [rightshift carry]
    ;; carry is set. add contents of #512 to #504
    ;;  and #514 to #506
    SWDD 0 #502 ; save
    LWDD 0 #512
    LWDD 1 #504
    ADD  1       ; add 512 + 504
    SWDD 0 #504
    BCD [add rightshift carry]
    BD [no add rightshift carry]

    [add rightshift carry]
    ;; carry was set when adding 512 + 504
    LWDD 0 #514
    INC
    SWDD 0 #514

    [no add rightshift carry]
    LWDD 0 #514
    LWDD 1 #506
    ADD  1
    SWDD 0 #506 ; store #506 part

    [no rightshift carry]
    ;; no carry set, we have to shift either #512 or
    ;; #514 according to flag in #516
    LWDD 0 #518
    BZD [shift 512]
    BD [shift 514]

    [shift 512]
    LWDD 0 #512
    SLL
    SWDD 0 #512
    BZD [shift 512 carry]
    BD [mainloop second rightshift]

    [shift 512 carry]
    CLR 0
    INC
    SWDD 0 #516 ; now #514 should be shifted. we set
                ; #516 to 1
    LWDD 0 #514
    INC
    SWDD 0 #514
    BD [mainloop second rightshift]

    [shift 514]
    LWDD 0 #514
    SLL
    SWDD 0 #514
    BD [mainloop second rightshift]

[end]