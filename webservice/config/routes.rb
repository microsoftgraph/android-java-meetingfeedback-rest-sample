=begin
    Copyright (c) Microsoft. All rights reserved. Licensed under the MIT license. 
    See LICENSE in the project root for license information.
=end
Rails.application.routes.draw do

  resources :meetings, :defaults => { :format => 'json' } do
    resource :rating

  end

  get 'mymeetings', controller: :meetings,
                       action: :mymeetings

end
