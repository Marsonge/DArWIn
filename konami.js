
<!DOCTYPE html>
<html>

  <head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">

  <title>Konami-JS: The original easter-egg script</title>
  <meta name="description" content="">
  <link rel="canonical" href="http://code.snaptortoise.com/konami-js//">
  <!-- Latest compiled and minified CSS -->
  <link href="https://fonts.googleapis.com/css?family=Source+Code+Pro:200,300,400,700,900|Source+Sans+Pro:300,300i,400,700,700i" rel="stylesheet">  
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">
  <link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/font-awesome/4.6.3/css/font-awesome.min.css' />
  <link rel="stylesheet" href="/konami-js//css/main.css">
</head>


  <body>

    <div class='moving-background'>
  <header class='primary-header'>  
      <div class='container'>
          <div class='header-text'>
            <h1><a href="/konami-js//"> Konami-JS <i class='fa fa-gamepad'></i></a></h1>
            <p>A quick and silly script for adding the Konami Code easter egg to your site.  Compatible with gestures on smartphones and tablets as well.</p>
            <!-- <p class="view"><a href="https://github.com/snaptortoise/konami-js">View the Project on GitHub <small>snaptortoise/konami-js</small></a></p> -->
          </div>


            <a class='btn btn-default btn-lg' href="https://github.com/snaptortoise/konami-js/zipball/master"><i class='fa fa-download'></i> Download</a>
            <a class='btn btn-primary btn-lg' href="https://github.com/snaptortoise/konami-js"><fa class='fa fa-github'></fa> View On <strong>GitHub</strong></a>

      </div>
  </header>
</div>

    <div class='container'>
      <div class='primary-content'>
        <h3>About This Project</h3>
<p>Every site should have an implementation of the Konami Code. It makes things more fun! If you're unfamiliar with it, the Konami Code is a "cheat code" that appeared in many of Konami's video games going all the way back to 1986.  It was typically entered on a Nintendo controller. Now it's often used on websites to show silly things to visitors when the code is "unlocked."</p>

<p>Konami-JS includes support for gestures on smartphones and tablets.  Technically the code becomes "up, up, down, down, left, right, left, right, tap, tap, tap," on these devices but... that's close enough!</p>

<p>Support for touch gestures is automatically loaded when <code>konami.load()</code> is called.</p>

<h3>Installing</h3>

<p>You can install Konami-JS using <a href='https://bower.io'>Bower</a> like so:</p>

<pre><code>bower install konami-js</code></pre>

<p>You can also <a href='https://github.com/snaptortoise/konami-js/archive/master.zip'>download the files</a> from GitHub and stick <code>konami.js</code> in your project.</p>

<h3>Getting Started</h3>
<p>The simplest version of a Konami-JS implementation looks like this:</p>

<pre><code>var easter_egg = new Konami('http://your-special-easter-egg-website.com');
</code></pre>

<p>This will redirect the usegr to a specified website upon successfully completing the Konami Code.</p>
<p>You can also pass a function instead of a string:</p>

<pre><code>var easter_egg = new Konami(function() { alert('Konami Code!')});
</code></pre>

<p>This will call the function when the code is successfully entered.</p>

<h3>Coming Soon</h3>
<ul>
  <li>How to present different easter eggs for gesture versus keyboard input.</li>
  <li>How to override the default code for both keyboard and gesture.</li>
  <li>More complex examples beyond a simple redirect.</li>
  <li>How to track the Konami Code using Google Analytics.</li>
  <li>Other examples!</li>
</ul>

<h3>
<a name="as-seen-on" class="anchor" href="#as-seen-on"><span class="octicon octicon-link"></span></a>As Seen On...</h3>

<ul>
<li><a href='http://www.forbes.com/sites/firewall/2010/06/15/newsweek-reports-the-zombie-invasion/#4f8d0c82319c'>Newsweek Zombies</a></li>
<li>Marvel</li>
<li><a href="http://mashable.com/2010/07/31/konami-code-sites">Almost half the sites in this Mashable article</a></li>
<li><a href="http://uxdesign.smashingmagazine.com/2012/04/26/gamification-ux-users-win-lose/">Smashing Magazine</a></li>
<li>...and MANY more!</li>
</ul>

      </div>
    </div>

    <footer class="site-footer">
  <div class='container'>
    <hr />
      <p class='text-center'>

        A silly web-toy lovingly cobbled together in 2009 by <a href='http://george.mand.is'>George Mandis</a> of <a href='https://snaptortoise.com?konami'>Snaptortoise</a>.  <br />
        <a href='mailto:info+konami@snaptortoise.com'>Available</a> for consulting, development and teaching.
      </p>
      <p class='text-center'><i class='fa fa-heart'></i></p>
      <p class='text-center'>
        <strong>Konami-JS 1.4.5</strong>. Licensed under the <a href='(http://opensource.org/licenses/MIT'>MIT License</a>. <br /> Thanks to <a href='http://getbootstrap.com/'>Bootstrap</a>, <a href='http://fontawesome.io/'>Font Awesome </a>and <a href='http://subtlepatterns.com'>Subtle Patterns</a> for the resources to build-out this page. <br />
      </p>
    
  </div>
</footer>


    
    <script src='konami.js'></script>
    <script src='easteregg.js'></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>
    <script>
    (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
    (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
    m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
    })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

    ga('create', 'UA-44252170-1', 'snaptortoise.com');
    ga('send', 'pageview');
    </script>
    
  </body>

</html>
