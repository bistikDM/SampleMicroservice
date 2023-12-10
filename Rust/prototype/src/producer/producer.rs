use crate::structs::bar::Bar;
use crate::structs::foo::Foo;
use crate::utils::word_list::WordList;

pub trait Producer {
    fn produce_foo_data(&self, word_list: &mut WordList) -> Foo;
    fn produce_bar_data(&self, word_list: &mut WordList) -> Bar;
}