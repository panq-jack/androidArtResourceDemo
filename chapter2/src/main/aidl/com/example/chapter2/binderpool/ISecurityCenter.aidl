package com.example.chapter2.binderpool;

interface ISecurityCenter {
    String encrypt(String content);
    String decrypt(String password);
}