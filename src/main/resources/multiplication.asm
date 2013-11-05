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
118 BZD #312 ; [end] ; if 0, goto end
120 SLL ; negative if carry
122 BCD #126 ; [first number negative]
124 BD  #140 ; [first number positive]

; [first number negative]
126 LWDD 0 #500
128 NOT
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
150 BZD #312 ; [end] ; if 0, goto end
152 NOT
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
172 BD #192 ; [none negative]

; [one or both negative]
;; calculate negation
174 LWDD 0 #508
176 AND 1 ; if both are neg => 1;
          ; if one is neg => 0
178 BZD #184 ; [set one is neg] only one is neg

; [both are neg]
180 CLR 0
182 BD #190 ; [store neg]

; [set one is neg]
184 CLR 0
186 INC
188 BD #190 ; [store neg]

; [store neg]
190 SWDD 0 #508 ; store 0 or 1 in #508

; [none negative]
;; everything prepared, start real work
192 LWDD 0 #500
194 SWDD 0 #512 ; store first number into calculation memory

; [mainloop second rightshift]
    196 LWDD 0 #502
    198 BZD #266 ; [make 32 bit] ; if second is 0 => goto end
    200 SRL ; right shift. for every rightshift one leftshift
            ; on #512 or #514 if not carry is set
    202 SWDD 0 #502  ; save
    204 BCD #208 ; [add values]
    206 BD  #234 ; [shift values]

    ; [add values]
        ; 504 = 504 + 512
        208 LWDD 0 #512
        210 LWDD 1 #504
        212 ADD  1
        214 SWDD 0 #504
        216 BCD #220  ; [add rightshift carry] carry bit is set
                      ; must be added to #514
        218 BD  #226  ; [no add rightshift carry]

        ; [add rightshift carry]
        ;; carry was set when adding 512 + 504
        220 LWDD 0 #506
        222 INC
        224 SWDD 0 #506

        ; [no add rightshift carry]
        ;; just add current value of #514 to #506
        ;; 506 = 506 + 514
        226 LWDD 0 #514
        228 LWDD 1 #506
        230 ADD  1
        232 SWDD 0 #506

    ; [shift values]
        ; [shift 512]
        234 LWDD 0 #512
        236 SLA
        238 SWDD 0 #512
        240 BCD #244 ; [shift inc]
        242 BD #258 ; [shift only]

        ; [shift inc]
        244 LWDD 0 #514
        246 SLA
        248 SWDD 0 #514
        250 LWDD 0 #514
        252 INC
        254 SWDD 0 #514
        256 BD #196 ; [mainloop second rightshift]

        ; [shift only]
        258 LWDD 0 #514
        260 SLA
        262 SWDD 0 #514
        264 BD #196 ; [mainloop second rightshift]

; [make 32 bit]
266 LWDD 0 #506
268 SRL
270 SWDD 0 #506
272 BCD #276 ; [or 32768]
274 BD #290 ; [handle negative]

; [or 32768]
;; add a 1 in front of #504
276 CLR 0
278 ADDD #-32768
280 SWDD 0 #516
282 LWDD 1 #516
284 LWDD 0 #504
286 OR 1
288 SWDD 0 #504

; [handle negative]
; do the neg work. make #504 and #506
;   negative if #508 is set
290 LWDD 0 #508
292 BNZD #296 ; [make negative]
294 BD   #312 ; [end]

; [make negative]
296 LWDD 0 #504
298 NOT
300 INC
302 SWDD 0 #504
304 LWDD 0 #506
306 NOT
308 INC
310 SWDD 0 #506

; [end]
312 END