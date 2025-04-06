<body>

  <p><strong>📦 CustomToastMessage</strong></p>
  <p>A beautifully customizable Snackbar library for Android built with Kotlin.  
  Easily show snack messages with icons, actions, borders, colors, and much more — all with a single builder call.</p>

  <p><strong>🚀 Dependency</strong></p>
  <p>Add this to your module <code>build.gradle</code>:</p>
  <pre><code>implementation 'com.github.vaishaliichandel:CustomToastMessage:1.0.0'</code></pre>

  <p>Ensure JitPack is included in your project-level <code>settings.gradle.kts</code>:</p>
  <pre><code>dependencyResolutionManagement {
    repositories {
        maven { url = uri("https://jitpack.io") }
    }
}</code></pre>

  <p><strong>⚡️ How to Use</strong></p>
  <pre><code>Snacking.Builder(view, "Your message here")
    .build()
    .show()</code></pre>

  <p><strong>🍭 Features & Examples</strong></p>

  <p><u>✅ Basic Snackbar</u></p>
  <pre><code>Snacking.Builder(rootView, "Hello! this is basic message")
    .build()
    .show()</code></pre>

  <p><u>✨ Elevation</u></p>
  <pre><code>.elevation(5F)</code></pre>

  <p><u>🛠 Icon with Tint</u></p>
  <pre><code>.icon(R.drawable.ic_info, R.color.teal_200)   or  .icon(R.drawable.ic_info,"#00594A") </code></pre>
 


  <p><u>🧲 Action Button with custom color </u></p>
  <pre><code>.action("Dismiss", R.color.teal_200, object : Snacking.Callback {
    override fun onActionClick(snackBar: Snacking?) {
        snackBar?.dismiss()
    }
})</code></pre>

  <p><u>❌ Close Icon</u></p>
  <pre><code>.actionClose(R.drawable.ic_close, R.color.teal_200, object : Snacking.Callback {
    override fun onActionClick(snackBar: Snacking?) {
        snackBar?.dismiss()
    }
})</code></pre>

  <p><u>🎯 Corner Radius</u></p>
  <pre><code>.cornerRadius(30F)</code></pre>

  <p><u>🧩 Custom Corner Radius</u></p>
  <pre><code>.cornerRadius(15f, 0f, 0f, 15f)</code></pre>

  <p><u>📏 Border with Color</u></p>
  <pre><code>.border(2F, R.color.colorPrimary)
.cornerRadius(10F)</code></pre>

  <p><u>🎨 Background Color</u></p>
  <pre><code>.backgroundColor(R.color.purple_200)</code></pre>

  <p><u>🌈 Text Color</u></p>
  <pre><code>.textColor(R.color.teal_200)</code></pre>

  <p><u>🆎 Font Style & Family</u></p>
  <pre><code>.fontFamily(R.font.montserrat)
.textStyle(Snacking.BOLD_ITALIC)</code></pre>

  <p><u>🧭 Top Position</u></p>
  <pre><code>.position(Snacking.TOP)</code></pre>

  <p><u>📏 Max Lines</u></p>
  <pre><code>.messageMaxLines(2)</code></pre>

  <p><u>⏳ INDEFINITE Duration</u></p>
  <pre><code>.duration(Snacking.INDEFINITE)</code></pre>

  <p><u>🧼 Remove Margin</u></p>
  <pre><code>.removeMargin()</code></pre>

  <p><strong>📸 Check out Video for examples</strong></p>
https://github.com/user-attachments/assets/09c2d332-cabe-4ee7-b347-43731dc75013</p>

  <p><strong>💡 Contribution</strong></p>
  <p>Feel free to <strong>fork</strong>, open <strong>issues</strong>, or submit <strong>pull requests</strong>.  
  Show some ❤️ by giving this repo a ⭐!</p>

  <p><strong>👩‍💻 Developer</strong></p>
  <p><strong>Vaishali Chandel</strong></p>
  <p><a href="https://github.com/vaishaliichandel" target="_blank">GitHub Profile</a></p>

</body>



