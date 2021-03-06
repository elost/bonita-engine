package org.bonitasoft.engine.api.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;

import org.junit.Test;

public class DeepRegexFileFilterTest {

    @Test
    public void acceptShouldWorkForMatchingPattern() {
        final String pattern = "folder/sub/.*\\.txt";
        final String parentPatternPathname = "/media/drive/some_folder";
        assertThat(new DeepRegexFileFilter(new File(parentPatternPathname), pattern).accept(new File("/media/drive/some_folder/folder/sub/matchingFile.txt")))
                .isTrue();
    }

    @Test
    public void acceptShouldWorkForMatchingPatternOnFolderWithTrailingSlash() {
        final String pattern = "folder/sub/.*\\.txt";
        final String parentPatternPathname = "/home/some_folder/";
        assertThat(new DeepRegexFileFilter(new File(parentPatternPathname), pattern).accept(new File("/home/some_folder/folder/sub/matchingFile.txt")))
                .isTrue();
    }

    @Test
    public void acceptShouldRejectNonMatchingFile() {
        final String pattern = "folder/sub/.*\\.txt";
        final String parentPatternPathname = "/home/some_folder/";
        assertThat(new DeepRegexFileFilter(new File(parentPatternPathname), pattern).accept(new File("/home/some_folder/folder/sub/someReport.pdf")))
                .isFalse();
    }

    @Test
    public void acceptShouldMatchDeepSubFolders() {
        final String pattern = "folder/.*\\.txt";
        final String parentPatternPathname = "/home";
        assertThat(new DeepRegexFileFilter(new File(parentPatternPathname), pattern).accept(new File("/home/folder/sub/sub2/fileHiddenInDeepFolder.txt")))
                .isTrue();
    }

    @Test
    public void acceptShouldNotMatchSlashBeginingPatterns() {
        final String pattern = "/folder/.*\\.txt";
        final String parentPatternPathname = "/home";
        assertThat(new DeepRegexFileFilter(new File(parentPatternPathname), pattern).accept(new File("/home/folder/sub/sub2/fileHiddenInDeepFolder.txt")))
                .isFalse();
    }

}
