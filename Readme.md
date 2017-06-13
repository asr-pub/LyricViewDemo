[![](https://jitpack.io/v/zhengken/LyricViewDemo.svg)](https://jitpack.io/#zhengken/LyricViewDemo)
# LyricView
LyricView is a powerful and flexible custom view to display lyrics within music player under Android 
## Screenshot
![](/screenshot/lyricview.png)
[LyricViewDemo.apk][1]      [YouTube][2]
## Usage
### Gradle dependency
    step 1 Add the JitPack repository to your build file
    allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
	
	step 2 Add the dependency
    dependencies {
            compile 'com.github.zhengken:LyricViewDemo:v1.2'
    }

### XML code
    //step 1
    <me.zhengken.lyricview.LyricView
            android:id="@+id/custom_lyric_view"
            android:layout_width="match_parent"
            android:layout_height="match_parent" />
### Java code
    //step 2
    LyricView mLyricView = (LyricView)findViewById(R.id.custom_lyric_view);
    
    //step 3
    mLyricView.setLyricFile(lyricFile);
    
    //step 4, update LyricView every interval
    mLyricView.setCurrentTimeMillis(progress);
    
    //step 5, implement the interface when user drag lyrics and click the play icon
    mLyricView.setOnPlayerClickListener(new LyricView.OnPlayerClickListener() {
            @Override
            public void onPlayerClicked(long progress, String content) {
                
            }
        });
### XML attributes
|Attributes|Format|Default|Description|
|:--|:--|:--|:--|
|fadeInFadeOut|boolean|false|Enable lyrics fadeInFadeOut or not|
|hint|string|No Lyrics|Display when not exist lyric file|
|hintColor|color|`#FFFFFF`|The color of hint text|
|textSize|dimension|16sp|The text size of lyrics|
|textColor|color|`#8D8D8D`|The color of lyrics|
|highlightColor|color|`#FFFFFF`|The color of current lyric that playing|
|textAlign|enum|CENTER|The alignment of lyrics|
|maxLength|dimension|300dp|Line feed when lyric'width beyond maxLength|
|lineSpace|dimension|25dp|Line space|
### Java API
|Methods|Description|
|:--|:--|
|`setOnPlayerClickListener(OnPlayerClickListener listener)`|Callback when click the play icon|
|`setAlignment(@Alignment int alignment)`|Set the alignment of the lyrics|
|`setCurrentTimeMillis(long current)`|Scroll lyrics to the specify TimeMillis|
|`setLyricFile(File file)`|Set the lyric file, and auto set the charset by `juniversalchardet-1.0.3`|
|`setLyricFile(File file, String charset)`|Set the lyric file with the specified charset|
|`setTypeface(Typeface typeface)`|Set the typeface of lyrics|
|`reset()`|Reset the LyricView|
## Thanks
[@码农小阿飞][3]
## License
    Copyright 2016 zhengken

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


  [1]: https://github.com/zhengken/LyricViewDemo/tree/master/sample
  [2]: https://youtu.be/Mjp9I6-0KHs
  [3]: http://blog.csdn.net/mario_0824