..  Copyright (C)  Brad Miller, David Ranum, Jeffrey Elkner, Peter Wentworth, Allen B. Downey, Chris
    Meyers, and Dario Mitchell.  Permission is granted to copy, distribute
    and/or modify this document under the terms of the GNU Free Documentation
    License, Version 1.3 or any later version published by the Free Software
    Foundation; with Invariant Sections being Forward, Prefaces, and
    Contributor List, no Front-Cover Texts, and no Back-Cover Texts.  A copy of
    the license is included in the section entitled "GNU Free Documentation
    License".

.. qnum::
   :prefix: select-2-
   :start: 1

Logical operators
-----------------

There are three **logical operators**: ``and``, ``or``, and ``not``. The
semantics (meaning) of these operators is similar to their meaning in English.
For example, ``x > 0 and x < 10`` is true only if ``x`` is greater than 0 *and*
at the same time, x is less than 10.  How would you describe this in words?  You would say that
x is between 0 and 10, not including the endpoints.

``n % 2 == 0 or n % 3 == 0`` is true if *either* of the conditions is true,
that is, if the number is divisible by 2 *or* divisible by 3.  In this case, one, or the other, or
both of the parts has to be true for the result to be true.

Finally, the ``not`` operator negates a boolean expression, so ``not  x > y``
is true if ``x > y`` is false, that is, if ``x`` is less than or equal to
``y``.

.. activecode:: chp05_3

    x = 5
    print(x > 0 and x < 10)

    n = 25
    print(n % 2 == 0 or n % 3 == 0)


.. admonition:: Common Mistake!

	There is a very common mistake that occurs when programmers try to write boolean expressions.  For example, what if we have a variable ``number`` and we want to check to see if its value is 5,6, or 7.  In words we might say: "number equal to 5 or 6 or 7".  However, if we translate this into Python, ``number == 5 or 6 or 7``, it will not be correct.  The ``or`` operator must join the results of three equality checks.  The correct way to write this is ``number == 5 or number == 6 or number == 7``.  This may seem like a lot of typing but it is absolutely necessary.  You cannot take a shortcut.


**Check your understanding**

.. mchoice:: test_question6_2_1
   :answer_a: x &gt; 0 and &lt; 5
   :answer_b: 0 &lt; x &lt; 5
   :answer_c: x &gt; 0 or x &lt; 5
   :answer_d: x &gt; 0 and x &lt; 5
   :correct: d
   :feedback_a: Each comparison must be between exactly two values.  In this case the right-hand expression &lt; 5 lacks a value on its left.
   :feedback_b: This is tricky.  Although most other programming languages do not allow this syntax, in Python, this syntax is allowed.  However, you should not use it.  Instead, make multiple comparisons by using and or or.
   :feedback_c: Although this is legal Python syntax, the expression is incorrect.  It will evaluate to true for all numbers that are either greater than 0 or less than 5.  Because all numbers are either greater than 0 or less than 5, this expression will always be True.
   :feedback_d: Yes, with an and keyword both expressions must be true so the number must be greater than 0 an less than 5 for this expression to be true.

   What is the correct Python expression for checking to see if a number stored in a variable x is between 0 and 5.
   
   .. tag test_questions6_2_1: Boolean, Boolean Expression, Logical Operators
   
.. mchoice:: test_question6_2_2
    :answer_a: x / 2 == True and x / 7 == False
    :answer_b: x % 2 == 0 and x % 7 == 0
    :answer_c: x % 2 == 0 or x % 7 == 0
    :answer_d: x / 7 == False or x / 2 == True
    :correct: b
    :feedback_a: x / 2 == True is not how you determine if a number is even. x / 7 == False is not how you determine if a number is divisible by 7.
    :feedback_b: This expression only evaluates to True if x is evenly divisible by 2 AND evenly divisible by 7. Both comparisons must be True in order for the whole expression to be True.
    :feedback_c: This expression will evaluate to True if either comparison is True. But we want an expression that depends on both comparisons to be True.
    :feedback_d: x / 7 == False is not how you determine if a number is divisible by 7. x / 2 == True is not how you determine if a number is even.
    
    Which of these expressions evaluates to True if x is equal to a number that is both even and divisible by 7?
    
    .. tag test_questions6_2_2: Boolean, Boolean Expression, Logical Operators
    
.. mchoice:: test_question6_2_3
    :multiple_answers:
    :answer_a: x &gt;= 3 or x &lt; 20
    :answer_b: not x &lt; 3 and not x &gt;= 20
    :answer_c: x &gt;= 3 and not x &gt;= 20
    :answer_d: x &gt;= 3 and x &lt; 20
    :correct: a,b,c,d
    
    Which of these expressions evaluates to True if x is equal to 10? Select all that apply. 

    .. tag test_questions6_2_3: Boolean, Boolean Expression, Logical Operators
    
.. mchoice:: test_question6_2_4
    :answer_a: 2
    :answer_b: 1
    :answer_c: &gt;= 1
    :answer_d: &gt;= 0
    :correct: d
    :feedback_a: One expression can have more than two logical operators.
    :feedback_b: One expressions can have more than one logical operator.
    :feedback_c: One expression can have less than one logical operator.
    :feedback_d: One expression can have any number of logical operators within its computational power.
    
    How many logical operators can you have in one expression? 

    .. tag test_questions6_2_4: Logical Operators
    
.. mchoice:: test_question6_2_5
    :answer_a: True
    :answer_b: False
    :answer_c: 3
    :correct: a
    :feedback_a: In the context of a Boolean expression, 3 evalutes to True, so this expression evaluates to True.
    :feedback_b: Neither half of the expression evaluates to False.
    :feedback_c: 3 outside of the context of a Boolean expression would evalute to 3, but because it is being compared in a Boolean expression it defaults to True.
    
    If x = 2, what will this expression evaluate to? 3 and x > 0 

    .. tag test_questions6_2_5: Boolean, Boolean Expressions, Logical Operators
    
