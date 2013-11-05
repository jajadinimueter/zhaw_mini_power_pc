;; INITIALIZATION
;;

100 CLR 0
102 SWDD 0 #504 ; result 1
104 SWDD 0 #506 ; restlt 2
106 SWDD 0 #508 ; neg flag 1
108 SWDD 0 #510 ; neg flag 2
110 SWDD 0 #512 ; one multiplication 1
112 SWDD 0 #514 ; one multiplication 2
114 SWDD 0 #516 ; which leftshift flag (#512 or #514)

;; determine negative flags

;; first number
116 LWDD 0 #500
118 BZD #284 ; [end] ; if 0, goto end
120 SLL ; negative if carry
122 BCD #126 ; [first number negative]
124 BD  #140 ; [first number positive]

; [first number negative]
126 LWDD 0 #500
128 NEG
130 INC
132 SWDD 0 #500
134 CLR 0
136 INC
138 SWDD 0 #508 ; write 1 to #508

; [first number positive]
; check the second number
140 LWDD 0 #502
142 SLL
144 BCD #148 ; [second number negative]
146 BD  #164 ; [second number positive]

; [second number negative]
148 LWDD 0 #502
150 BZD #284 ; [end] ; if 0, goto end
152 NEG
154 INC
156 SWDD 0 #502
158 CLR 0
160 INC
162 SWDD 0 #510 ; write 1 to #510

; [second number positive]
;; calculate negation
164 LWDD 0 #508
166 LWDD 1 #510
168 OR 1
170 BNZD #174 ; [one or both negative]
172 BD #182 ; [none negative]

; [one or both negative]
;; calculate negation
174 LWDD 0 #508
176 AND 1 ; if both are neg => 1 then 0;
          ; if one is neg => 0, then 1
178 NOT
180 SWDD 0 #508

; [none negative]
;; everything prepared, start real work
182 LWDD 0 #500
184 SWDD 0 #512 ; store first number into calculation memory

; [mainloop second rightshift]
    186 LWDD 0 #502
    188 BRD #262 ; [handle negative] ; if second is 0 => goto end
    190 SRL ; right shift. for every rightshift one leftshift
        ; on #512 or #514 if not carry is set
    192 SWDD 0 #502 ; save
    194 BCD #198 ; [rightshift carry]
    196 BD  #224 ; [no rightshift carry]

    ; [rightshift carry]
    ;; carry is set. add contents of #512 to #504
    ;;  and #514 to #506
    198 LWDD 0 #512
    200 LWDD 1 #504
    202 ADD  1       ; add 512 + 504
    204 SWDD 0 #504
    206 BCD #210 ; [add rightshift carry]
    208 BD #216 ; [no add rightshift carry]

    ; [add rightshift carry]
    ;; carry was set when adding 512 + 504
    210 LWDD 0 #514
    212 INC
    214 SWDD 0 #514

    ; [no add rightshift carry]
    216 LWDD 0 #514
    218 LWDD 1 #506
    220 ADD  1
    222 SWDD 0 #506 ; store #506 part

    ; [no rightshift carry]
    ;; no carry set, we have to shift either #512 or
    ;; #514 according to flag in #516
    224 LWDD 0 #518
    226 BZD #230 ; [shift 512]
    228 BD #254 ; [shift 514]

    ; [shift 512]
    230 LWDD 0 #512
    232 SLL
    234 SWDD 0 #512
    236 BZD #240 ; [shift 512 carry]
    238 BD #186 ; [mainloop second rightshift]

    ; [shift 512 carry]
    240 CLR 0
    242 INC
    244 SWDD 0 #516 ; now #514 should be shifted. we set
                ; #516 to 1
    246 LWDD 0 #514
    248 INC
    250 SWDD 0 #514
    252 BD #186 ; [mainloop second rightshift]

    ; [shift 514]
    254 LWDD 0 #514
    256 SLL
    258 SWDD 0 #514
    260 BD #186 ; [mainloop second rightshift]

; [handle negative]
; do the neg work. make #504 and #506
;   negative if #508 is set
262 LWDD 0 #508
264 BNZD #268 ; [make negative]
266 BD   #284 ; [end]

; [make negative]
268 LWDD 0 #504
270 NEG
272 INC
274 SWDD 0 #504
276 LWDD 0 #506
278 NEG
280 INC
282 SWDD 0 #506

; [end]
284 END