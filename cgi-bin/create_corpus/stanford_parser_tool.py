#!/usr/bin/python3
# coding=UTF-8

import sys, argparse


def get_sentence(file_name):
    with open(file_name, 'r') as f:
        sentences = []
        for line in f:
            sentences.append(line)
    return sentences

def get_post_taggeer(sentences = [], output_file = None):
    from nltk.tag import StanfordPOSTagger
    result_list = []
    st = StanfordPOSTagger('create_corpus/stanford-postagger-full-2015-12-09/models/english-bidirectional-distsim.tagger',
            'create_corpus/stanford-postagger-full-2015-12-09/stanford-postagger-3.6.0.jar')
    with open(output_file, 'w') as output_f:
        for sen in sentences:
            result = st.tag(sen.split())
            if output_file:
                for wd in result:
                    string = '{0}/{1} '.format(wd[0], wd[1]) 
                    output_f.write(string)
                output_f.write('\n')
                result_list.append(result)
    return result_list

def get_parse(sentences = [], output_file = None):
    import os
    from nltk.parse import stanford
    os.environ['CLASSPATH'] = 'create_corpus/stanford-parser-full-2015-12-09/stanford-parser.jar'
    os.environ['STANFORD_MODELS'] = 'create_corpus/stanford-parser-full-2015-12-09/stanford-parser-3.6.0-models.jar'

    stanford_parser = stanford.StanfordParser(model_path='create_corpus/stanford-english-corenlp-2016-01-10-models/edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz')
    result_sentences = stanford_parser.raw_parse_sents(sentences)
    if output_file:
        with open(output_file, 'w') as output_f:
            for line in result_sentences:
                for _ in line:
                    output_f.write(str(_))
    return result_sentences

if '__main__' == __name__ :
    parser = argparse.ArgumentParser(sys.argv[0])
    parser.add_argument('option', choices=['tagger', 'parser'], help='provide a action you want to do')
    parser.add_argument('-I', '--Input', type=str, default='INPUT_FILE')
    parser.add_argument('-O', '--Output', type=str, default='OUTPUT_FILE')
    opt = parser.parse_args(sys.argv[1:])

    sentences_list = get_sentence(opt.Input)

    if opt.option == 'tagger':
        get_post_taggeer(sentences_list, opt.Output)
    elif opt.option == 'parser':
        get_parse(sentences_list, opt.Output)
