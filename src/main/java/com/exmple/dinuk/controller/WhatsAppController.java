package com.exmple.dinuk.controller;

import com.exmple.dinuk.dto.WhatappDTO;
import com.exmple.dinuk.service.WhatsAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/v1/send")
@CrossOrigin
public class WhatsAppController {
    @Autowired
    private WhatsAppService whatsAppService;
    @PostMapping(value = "/WhatsApp")
    public WhatappDTO SendWhatapp(@RequestBody WhatappDTO whatappDTO){

        return whatsAppService.sendWhatsAppMessage(whatappDTO);
    }
}
