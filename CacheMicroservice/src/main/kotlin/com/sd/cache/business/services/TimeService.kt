package com.sd.cache.business.services

import com.sd.cache.business.interfaces.ITimeService
import org.springframework.stereotype.Service
import java.text.SimpleDateFormat
import java.util.*

@Service
class TimeService : ITimeService {

    override fun getCurrentTime() : String {
        val formatter =  SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
        return formatter.format(Date())
    }
}