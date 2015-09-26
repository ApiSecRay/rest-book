Scenario: Chrissy orders a latte

Given a customer Chrissy
When she reads the menu
And she orders a tall whole milk caffe latte
Then she is due $2.75 
When she pays
Then she is handed a receipt
When she takes the receipt
Then she must wait for the barista
When the barista calls her name
Then her serving is ready
When she takes her serving
Then she is happy
