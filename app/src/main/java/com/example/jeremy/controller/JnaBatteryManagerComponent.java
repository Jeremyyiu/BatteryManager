package com.example.jeremy.controller;

import com.example.jeremy.controller.modules.AppModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class})
public interface JnaBatteryManagerComponent {
}
