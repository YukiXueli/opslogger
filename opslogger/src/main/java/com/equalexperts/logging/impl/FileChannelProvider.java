package com.equalexperts.logging.impl;

import java.io.Closeable;
import java.io.IOException;
import java.io.Writer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

/** Provide a convenience method to get a file channel pointing to a previously opened
 * writable file in UTF-8 corresponding to a given Path.
 */
public class FileChannelProvider {
    private final Path path;

    public FileChannelProvider(Path path) {
        this.path = path;
    }

    public Result getChannel() throws IOException {
        FileChannel fileChannel = FileChannel.open(path, CREATE, APPEND);
        Writer writer = Channels.newWriter(fileChannel, "UTF-8");
        return new Result(fileChannel, writer);
    }

    static class Result implements Closeable {
        final FileChannel channel;
        final Writer writer;

        Result(FileChannel channel, Writer writer) {
            this.channel = channel;
            this.writer = writer;
        }

        @Override
        public void close() throws IOException {
            this.writer.close();
        }
    }

    public Path getPath() {
        return path;
    }
}
