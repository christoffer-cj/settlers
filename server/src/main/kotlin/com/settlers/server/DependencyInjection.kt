package com.settlers.server

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan("com.settlers.server.services")
@ComponentScan("com.settlers.server.controllers")
class DependencyInjection {}