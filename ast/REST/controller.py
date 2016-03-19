#!/usr/bin/env python3
# -*- coding: utf-8 -*-
#.--. .-. ... .... -. - ... .-.-.- .. -.
import requests

from flask import jsonify
from flask_restful import Resource, Api

api = Api(app, prefix = '/api')
api.add_resource(SurveyController,
                 '/survey'

api.add_resource(AudioController,"")