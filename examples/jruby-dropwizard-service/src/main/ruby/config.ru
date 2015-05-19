require 'rubygems'
require 'sinatra/base'

class Hullo < Sinatra::Base
  get('/') { 'Hello, Wurld' }
end

run Hullo
