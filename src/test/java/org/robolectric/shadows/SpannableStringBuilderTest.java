package org.robolectric.shadows;

import android.text.SpannableStringBuilder;
import android.text.style.TypefaceSpan;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.TestRunners;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertNull;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.Assert.assertSame;
import static org.robolectric.Robolectric.shadowOf;

@RunWith(TestRunners.WithDefaults.class)
public class SpannableStringBuilderTest {

    @Test
    public void testAppend() throws Exception {
        SpannableStringBuilder builder = new SpannableStringBuilder("abc");
        builder.append('d').append("e").append("f");
        assertThat(builder.toString()).isEqualTo("abcdef");
    }

    @Test
    public void testLength() throws Exception {
        SpannableStringBuilder builder = new SpannableStringBuilder("abc");
        assertThat(builder.length()).isEqualTo(3);
    }

    @Test
    public void testReplace() throws Exception {
        SpannableStringBuilder builder = new SpannableStringBuilder("abc");
        assertThat(builder.replace(2, 3, "").toString()).isEqualTo("ab");
        assertThat(builder.replace(0, 2, "xyz").toString()).isEqualTo("xyz");
    }

    @Test
    public void testReplaceFromSquare() throws Exception {
        SpannableStringBuilder builder = new SpannableStringBuilder("abcd");
        builder.replace(1,3,"XXX");
        assertThat(builder.toString()).isEqualTo("aXXXd");
    }

    @Test
    public void testInsert() throws Exception {
        SpannableStringBuilder builder = new SpannableStringBuilder("abc");
        assertThat(builder.insert(1, "xy").toString()).isEqualTo("axybc");
    }

    @Test
    public void testDelete() throws Exception {
        SpannableStringBuilder builder = new SpannableStringBuilder("abc");
        assertThat(builder.length()).isEqualTo(3);
        builder.delete( 0, 3 );
        assertThat(builder.length()).isEqualTo(0);
    }    

    @Test
    public void testReplace_extraParams() throws Exception {
        SpannableStringBuilder builder = new SpannableStringBuilder("abcd");
        builder.replace(1,3,"ignoreXXXignore", 6, 9);
        assertThat(builder.toString()).isEqualTo("aXXXd");
    }

    @Test
    @Config(shadows = {ShadowSpannableString.class, ShadowSpannableStringBuilder.class, ShadowSpannableStringInternal.class, ShadowSpannableString.class})
    public void setSpan_canAssignSpanToSubsequence() throws Exception {
        SpannableStringBuilder builder = new SpannableStringBuilder("abcd");
        ShadowSpannableStringBuilder shadowBuilder = shadowOf(builder);
        TypefaceSpan typeface1 = new TypefaceSpan("foo");
        TypefaceSpan typeface2 = new TypefaceSpan("foo");
        builder.setSpan(typeface1, 0, 2, 0);
        builder.setSpan(typeface2, 3, 3, 0);
        assertSame(typeface1, shadowBuilder.getSpanAt(0));
        assertSame(typeface1, shadowBuilder.getSpanAt(1));
        assertSame(typeface1, shadowBuilder.getSpanAt(2));
        assertSame(typeface2, shadowBuilder.getSpanAt(3));
    }

    @Test
    @Config(shadows = {ShadowSpannableString.class, ShadowSpannableStringBuilder.class, ShadowSpannableStringInternal.class, ShadowSpannableString.class})
    public void setSpan_canHandleGaps() throws Exception {
        SpannableStringBuilder builder = new SpannableStringBuilder("abcd");
        TypefaceSpan typeface1 = new TypefaceSpan("foo");
        builder.setSpan(typeface1, 2, 3, 0);
        assertNull(shadowOf(builder).getSpanAt(0));
    }

    @Test
    @Config(shadows = {ShadowSpannableString.class, ShadowSpannableStringBuilder.class, ShadowSpannableStringInternal.class, ShadowSpannableString.class})
    public void getSpanAt_returnsNullIfNoSpanAssigned() throws Exception {
        SpannableStringBuilder builder = new SpannableStringBuilder("abcd");
        assertNull(shadowOf(builder).getSpanAt(4));
    }
}
