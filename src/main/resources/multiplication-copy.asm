100 CLR R0 // clear accu
102 SWDD R0, #504 // clear 304
104 SWDD R0, #506 // clear 506
106 SWDD R0, #514 // clear 514

108 LWDD R0, #502 // check zweite zahl auf 0
110 BZD #316

112 LWDD R0, #500 // check 1. zahl auf 0
114 BZD #316

116 SLL // check ob 1. zahl negativ
118 BCD #126 // erste zahl ist negativ

// erste zahl positiv
120 CLR R0
122 SWDD R0, #510 // initialisiere 510 mit 0
124 BD #138 // zu 138

// erste zahl ist negativ
	126 LWDD R0, #500 // erste zahl wieder laden
	128 NOT // umwandeln in positive zahl
	130 INC
	132 SWDD R0, #500 // zurückspeichern
	134 CLR R0 // akku auf 1 setzen
	136 INC

// erste zahl ist positiv
138 SWDD R0, #510 // init 510 with 0
140 CLR R0
142 ADDD #15 // akku auf 15 setzen
144 SWDD R0, #508 // 508 = 15

146 LWDD R0, #502 // 2. zahl lesen
148 SLL
150 BCD #158 // 2. zahl ist negativ

// 2. zahl ist positiv
152 CLR R0
154 SWDD R0, #512
156 BD #172

// zweite zahl in positive umwandeln
158 LWDD R0, #502
160 NOT
162 INC
164 SWDD R0, #502
166 CLR R0
168 INC
170 SWDD R0, #512
172 LWDD R0, #510

174 LWDD R1, #512
176 ADD R1 // im accu ist #510 -> am anfang 0. in 512# ist 1
178 DEC // accu = 0
180 BNZD #188

182 CLR R0
184 INC
186 SWDD R0, #514

188 LWDD R0, #500 // erste zahl laden
190 SWDD R0, #504 // nach #504 speichern

// { block 2. zahl links shiften
    192 LWDD R0, #508 // 508 laden: der counter
    194 DEC // counter--
    196 SWDD R0, #508 // counter store
    198 LWDD R0, #502 // zweite zahl laden
    200 SLA // links shift arithmetisch (* 2)
    202 SWDD R0, #502 // wieder speichern
// end block }

// ganz links -> fortfahren
204 BCD #208
// weitershiften
206 BD #192

// { begin block 208
    208 LWDD R0, #506 // initial nix drin
    210 SLA // shift left
    212 SWDD R0, #506 // wieder rausschreiben
    214 LWDD R0, #504 // 504 laden
    216 SLA // eins links schieben
    218 SWDD R0, #504 // wieder speichern
    220 BCD #224 // falls überlauf
    222 BD #230 // falls nicht überlauf

        // 504 überlauf, 506 eins erhöhen
        224 LWDD R0, #506
        226 INC
        228 SWDD R0, #506

    // kein überlauf
    // 502 * 2
    230 LWDD R0, #502
    232 SLA
    234 SWDD R0, #502

    236 BCD #240
    238 BD #258

        // falls überlauf in 502 shift
        240 LWDD R0, #504
        242 LWDD R1, #500
        244 ADD R1
        246 SWDD R0, #504
        248 BCD #252
        250 BD #258
        252 LWDD R0, #506
        254 INC
        256 SWDD R0, #506

    258 LWDD R0, #508
    260 DEC
    262 SWDD R0, #508
    264 BNZD #208
// } end block

266 LWDD R0, #506
268 SRA
270 BCD #274
272 BD #286
274 CLR R1
276 ADDD #16384
278 SLL
280 LWDD R1, #504
282 OR R1
284 SWDD R0, #504
286 LWDD R0, #514
288 BZD #316
290 LWDD R0, #506
292 NOT
294 INC
296 SWDD R0, #506
298 LWDD R0, #504
300 NOT
302 INC
304 SWDD R0, #504
306 BCD #310
308 BD #316
310 LWDD R0, #506
312 DEC
314 SWDD R0, #506
316 END
