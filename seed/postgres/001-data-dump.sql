SET client_encoding = 'UTF8';
SET client_min_messages = warning;

--
-- tags
--

ALTER TABLE public.tags DISABLE TRIGGER ALL;

INSERT INTO public.tags (id, created_at, created_by, description, name, slug, type, updated_at, updated_by, version) VALUES ('019d624f-ae95-7347-8c31-d32ba9fc33b4', '2026-04-06 10:21:22.455359+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, 'Боевик', 'boevik', 'GENRE', NULL, NULL, 0);
INSERT INTO public.tags (id, created_at, created_by, description, name, slug, type, updated_at, updated_by, version) VALUES ('019d6255-97ed-73ae-a5af-b2208eba3a41', '2026-04-06 10:27:49.871398+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, 'Комедия', 'komediya', 'GENRE', NULL, NULL, 0);
INSERT INTO public.tags (id, created_at, created_by, description, name, slug, type, updated_at, updated_by, version) VALUES ('019d6255-e227-7ba5-adb5-b73636ed182f', '2026-04-06 10:28:08.872173+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, 'Сверхъестественное', 'sverhestestvennoe', 'GENRE', NULL, NULL, 0);
INSERT INTO public.tags (id, created_at, created_by, description, name, slug, type, updated_at, updated_by, version) VALUES ('019d6255-f62a-71fb-90f5-c26b2ed28191', '2026-04-06 10:28:13.99423+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, 'Сёнен', 'senen', 'GENRE', NULL, NULL, 0);
INSERT INTO public.tags (id, created_at, created_by, description, name, slug, type, updated_at, updated_by, version) VALUES ('019d6256-15c4-766e-987f-771bf8d34ca7', '2026-04-06 10:28:22.08451+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, 'Трагедия', 'tragediya', 'GENRE', NULL, NULL, 0);
INSERT INTO public.tags (id, created_at, created_by, description, name, slug, type, updated_at, updated_by, version) VALUES ('019d6256-2a69-7fae-a703-b4634d7466bc', '2026-04-06 10:28:27.370082+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, 'Ужасы', 'uzhasy', 'GENRE', NULL, NULL, 0);
INSERT INTO public.tags (id, created_at, created_by, description, name, slug, type, updated_at, updated_by, version) VALUES ('019d6256-3d3a-79db-a9a0-247341d7e891', '2026-04-06 10:28:32.186711+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, 'Фэнтези', 'fentezi', 'GENRE', NULL, NULL, 0);
INSERT INTO public.tags (id, created_at, created_by, description, name, slug, type, updated_at, updated_by, version) VALUES ('019d6256-58a4-75ce-816a-e28779565488', '2026-04-06 10:28:39.204452+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, 'Антигерой', 'antigeroj', 'THEME', NULL, NULL, 0);
INSERT INTO public.tags (id, created_at, created_by, description, name, slug, type, updated_at, updated_by, version) VALUES ('019d6256-7772-7933-b0f2-83963d5e11a4', '2026-04-06 10:28:47.090674+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, 'ГГ Мужчина', 'gg-muzhchina', 'THEME', NULL, NULL, 0);
INSERT INTO public.tags (id, created_at, created_by, description, name, slug, type, updated_at, updated_by, version) VALUES ('019d6256-93c1-715c-b453-0337d1883129', '2026-04-06 10:28:54.337174+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, 'ГГ Женщина', 'gg-zhenshchina', 'THEME', NULL, NULL, 0);
INSERT INTO public.tags (id, created_at, created_by, description, name, slug, type, updated_at, updated_by, version) VALUES ('019d6256-b376-7feb-83d4-820fc1b09fdd', '2026-04-06 10:29:02.455096+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, 'Дружба', 'druzhba', 'THEME', NULL, NULL, 0);
INSERT INTO public.tags (id, created_at, created_by, description, name, slug, type, updated_at, updated_by, version) VALUES ('019d6256-d4ac-7a35-a3bb-7608439969ff', '2026-04-06 10:29:10.956721+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, 'Жестокий мир', 'zhestokij-mir', 'THEME', NULL, NULL, 0);
INSERT INTO public.tags (id, created_at, created_by, description, name, slug, type, updated_at, updated_by, version) VALUES ('019d6256-eabf-79e3-b6c6-ecc763119510', '2026-04-06 10:29:16.607698+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, 'Зомби', 'zombi', 'THEME', NULL, NULL, 0);
INSERT INTO public.tags (id, created_at, created_by, description, name, slug, type, updated_at, updated_by, version) VALUES ('019d6256-ff93-7df3-810b-fcb1cfa4ebcd', '2026-04-06 10:29:21.939951+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, 'Монстры', 'monstry', 'THEME', NULL, NULL, 0);
INSERT INTO public.tags (id, created_at, created_by, description, name, slug, type, updated_at, updated_by, version) VALUES ('019d6257-1c36-76a3-9fe8-200d521b6caf', '2026-04-06 10:29:29.270527+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, 'Насилие/жестокость', 'nasilie-zhestokost', 'THEME', NULL, NULL, 0);
INSERT INTO public.tags (id, created_at, created_by, description, name, slug, type, updated_at, updated_by, version) VALUES ('019d6257-3912-72e9-b7f2-212fbc7e8a61', '2026-04-06 10:29:36.658284+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, 'Огнестрельное оружие', 'ognestrelnoe-oruzhie', 'THEME', NULL, NULL, 0);
INSERT INTO public.tags (id, created_at, created_by, description, name, slug, type, updated_at, updated_by, version) VALUES ('019d6257-569c-7c8f-8630-5ca77f1e97ae', '2026-04-06 10:29:44.22088+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, 'Скрытие личности', 'skrytie-lichnosti', 'THEME', NULL, NULL, 0);
INSERT INTO public.tags (id, created_at, created_by, description, name, slug, type, updated_at, updated_by, version) VALUES ('019d6257-8dd2-7c18-b296-30616d0f9a3a', '2026-04-06 10:29:58.354852+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, 'Повседневность', 'povsednevnost', 'GENRE', NULL, NULL, 0);
INSERT INTO public.tags (id, created_at, created_by, description, name, slug, type, updated_at, updated_by, version) VALUES ('019d6257-b738-7672-be90-7b6f6c967830', '2026-04-06 10:30:08.952495+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, 'Психология', 'psihologiya', 'GENRE', NULL, NULL, 0);
INSERT INTO public.tags (id, created_at, created_by, description, name, slug, type, updated_at, updated_by, version) VALUES ('019d6257-d329-726a-83db-e494952737ec', '2026-04-06 10:30:16.105236+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, 'Школа', 'shkola', 'THEME', NULL, NULL, 0);
INSERT INTO public.tags (id, created_at, created_by, description, name, slug, type, updated_at, updated_by, version) VALUES ('019d6258-239e-7e2d-9f8c-66de81ea5b8f', '2026-04-06 10:30:36.703126+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, 'Безумие', 'bezumie', 'THEME', NULL, NULL, 0);
INSERT INTO public.tags (id, created_at, created_by, description, name, slug, type, updated_at, updated_by, version) VALUES ('019d6258-53d0-71d7-8467-50d48f9727d7', '2026-04-06 10:30:49.04024+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, 'Драма', 'drama', 'GENRE', NULL, NULL, 0);
INSERT INTO public.tags (id, created_at, created_by, description, name, slug, type, updated_at, updated_by, version) VALUES ('019d6258-a919-78f5-a85f-e4e6996c200d', '2026-04-06 10:31:10.873659+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, 'Постапокалиптика', 'postapokaliptika', 'GENRE', NULL, NULL, 0);
INSERT INTO public.tags (id, created_at, created_by, description, name, slug, type, updated_at, updated_by, version) VALUES ('019d6258-c7c3-7683-9711-9e3358899f74', '2026-04-06 10:31:18.7235+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, 'Триллер', 'triller', 'GENRE', NULL, NULL, 0);
INSERT INTO public.tags (id, created_at, created_by, description, name, slug, type, updated_at, updated_by, version) VALUES ('019d6258-e724-7a83-b806-d288707bbfa8', '2026-04-06 10:31:26.756747+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, 'Антиутопия', 'antiutopiya', 'THEME', NULL, NULL, 0);
INSERT INTO public.tags (id, created_at, created_by, description, name, slug, type, updated_at, updated_by, version) VALUES ('019d6259-05ff-7edd-b4cc-2c7bf7aebc4b', '2026-04-06 10:31:34.656013+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, 'Апокалипсис', 'apokalipsis', 'THEME', NULL, NULL, 0);
INSERT INTO public.tags (id, created_at, created_by, description, name, slug, type, updated_at, updated_by, version) VALUES ('019d6259-21fe-7018-876b-7a5af3d23494', '2026-04-06 10:31:41.822089+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, 'Выживание', 'vyzhivanie', 'THEME', NULL, NULL, 0);
INSERT INTO public.tags (id, created_at, created_by, description, name, slug, type, updated_at, updated_by, version) VALUES ('019d6259-4f0a-77a9-be57-1b510b341392', '2026-04-06 10:31:53.35457+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, 'ГГ имба', 'gg-imba', 'THEME', NULL, NULL, 0);
INSERT INTO public.tags (id, created_at, created_by, description, name, slug, type, updated_at, updated_by, version) VALUES ('019d6259-7263-7af5-8b93-a95c49beec76', '2026-04-06 10:32:02.40376+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, 'Геймеры', 'gejmery', 'THEME', NULL, NULL, 0);
INSERT INTO public.tags (id, created_at, created_by, description, name, slug, type, updated_at, updated_by, version) VALUES ('019d6259-b79c-71c2-8b52-6d97bdceb916', '2026-04-06 10:32:20.124191+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, 'Навыки/способности', 'navyki-sposobnosti', 'THEME', NULL, NULL, 0);
INSERT INTO public.tags (id, created_at, created_by, description, name, slug, type, updated_at, updated_by, version) VALUES ('019d6259-f67f-794f-8c5d-26aaee54fa9b', '2026-04-06 10:32:36.223658+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, 'Преступники/криминал', 'prestupniki-kriminal', 'THEME', NULL, NULL, 0);
INSERT INTO public.tags (id, created_at, created_by, description, name, slug, type, updated_at, updated_by, version) VALUES ('019d625a-1529-7ee1-8307-c748560de23d', '2026-04-06 10:32:44.074004+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, 'Разумные расы', 'razumnye-rasy', 'THEME', NULL, NULL, 0);
INSERT INTO public.tags (id, created_at, created_by, description, name, slug, type, updated_at, updated_by, version) VALUES ('019d625a-2f66-7c6a-ab5a-1f7758e412c7', '2026-04-06 10:32:50.790902+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, 'Спасение мира', 'spasenie-mira', 'THEME', NULL, NULL, 0);
INSERT INTO public.tags (id, created_at, created_by, description, name, slug, type, updated_at, updated_by, version) VALUES ('019d625a-4ba3-7c1c-8d6d-0a0a7473ac53', '2026-04-06 10:32:58.019836+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, 'Умный ГГ', 'umnyj-gg', 'THEME', NULL, NULL, 0);
INSERT INTO public.tags (id, created_at, created_by, description, name, slug, type, updated_at, updated_by, version) VALUES ('019d625a-6925-7178-ac84-4ddbdbe12b12', '2026-04-06 10:33:05.573172+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, 'Философия', 'filosofiya', 'THEME', NULL, NULL, 0);
INSERT INTO public.tags (id, created_at, created_by, description, name, slug, type, updated_at, updated_by, version) VALUES ('019d625a-dad8-73ca-910c-5a5da10b48aa', '2026-04-06 10:33:34.680591+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, 'Хикикомори', 'hikikomori', 'THEME', NULL, NULL, 0);
INSERT INTO public.tags (id, created_at, created_by, description, name, slug, type, updated_at, updated_by, version) VALUES ('019d625b-2ccc-7649-b866-bc3f33481443', '2026-04-06 10:33:55.660484+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, 'Холодное оружие', 'holodnoe-oruzhie', 'THEME', NULL, NULL, 0);
INSERT INTO public.tags (id, created_at, created_by, description, name, slug, type, updated_at, updated_by, version) VALUES ('019d625b-53b1-7c18-8404-27c519950f23', '2026-04-06 10:34:05.617895+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, 'Романтика', 'romantika', 'GENRE', NULL, NULL, 0);
INSERT INTO public.tags (id, created_at, created_by, description, name, slug, type, updated_at, updated_by, version) VALUES ('019d625b-8ce6-7af9-8482-fa80817b6e5c', '2026-04-06 10:34:20.262786+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, 'Сэйнэн', 'sejnen', 'GENRE', NULL, NULL, 0);
INSERT INTO public.tags (id, created_at, created_by, description, name, slug, type, updated_at, updated_by, version) VALUES ('019d625b-b3a4-7174-9d76-04eb6c8191e4', '2026-04-06 10:34:30.180192+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, 'Шантаж', 'shantazh', 'THEME', NULL, NULL, 0);
INSERT INTO public.tags (id, created_at, created_by, description, name, slug, type, updated_at, updated_by, version) VALUES ('019d625b-d7d7-7d02-b117-9eebd6e9e938', '2026-04-06 10:34:39.447912+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, 'Мистика', 'mistika', 'GENRE', NULL, NULL, 0);
INSERT INTO public.tags (id, created_at, created_by, description, name, slug, type, updated_at, updated_by, version) VALUES ('019d625b-f8b2-767a-bdf4-d3ea416d5d84', '2026-04-06 10:34:47.858487+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, 'Амнезия', 'amneziya', 'THEME', NULL, NULL, 0);
INSERT INTO public.tags (id, created_at, created_by, description, name, slug, type, updated_at, updated_by, version) VALUES ('019d625c-30f3-7fe3-be1a-f6529072e9e6', '2026-04-06 10:35:02.260073+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, 'Детектив', 'detektiv', 'GENRE', NULL, NULL, 0);
INSERT INTO public.tags (id, created_at, created_by, description, name, slug, type, updated_at, updated_by, version) VALUES ('019d625c-97d2-7620-b640-e10e889bc2bf', '2026-04-06 10:35:28.594467+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, 'Героическое фэнтези', 'geroicheskoe-fentezi', 'GENRE', NULL, NULL, 0);
INSERT INTO public.tags (id, created_at, created_by, description, name, slug, type, updated_at, updated_by, version) VALUES ('019d625c-a8e0-7d37-b3d2-8241d455c5e0', '2026-04-06 10:35:32.960912+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, 'Исекай', 'isekaj', 'GENRE', NULL, NULL, 0);
INSERT INTO public.tags (id, created_at, created_by, description, name, slug, type, updated_at, updated_by, version) VALUES ('019d625c-f61b-7c31-ad50-4d59af12220b', '2026-04-06 10:35:52.73209+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, 'Магия', 'magiya', 'GENRE', NULL, NULL, 0);
INSERT INTO public.tags (id, created_at, created_by, description, name, slug, type, updated_at, updated_by, version) VALUES ('019d625d-3a96-71d7-b963-e9782b7c7721', '2026-04-06 10:36:10.262212+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, 'Маги/волшебники', 'magi-volshebniki', 'THEME', NULL, NULL, 0);
INSERT INTO public.tags (id, created_at, created_by, description, name, slug, type, updated_at, updated_by, version) VALUES ('019d625d-60de-75db-aeb3-a2aa270b6fd1', '2026-04-06 10:36:20.062465+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, 'Волшебные существа', 'volshebnye-sushchestva', 'THEME', NULL, NULL, 0);
INSERT INTO public.tags (id, created_at, created_by, description, name, slug, type, updated_at, updated_by, version) VALUES ('019d625d-9221-7c72-8373-464f7bd7b713', '2026-04-06 10:36:32.673883+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, 'Владыка демонов', 'vladyka-demonov', 'THEME', NULL, NULL, 0);
INSERT INTO public.tags (id, created_at, created_by, description, name, slug, type, updated_at, updated_by, version) VALUES ('019d625d-ba04-707a-a543-9d52166318ce', '2026-04-06 10:36:42.884119+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, 'Приключения', 'priklyucheniya', 'GENRE', NULL, NULL, 0);
INSERT INTO public.tags (id, created_at, created_by, description, name, slug, type, updated_at, updated_by, version) VALUES ('019d625e-239c-725e-9f05-0dd9c9677400', '2026-04-06 10:37:09.916283+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, 'Гильдии', 'gildii', 'THEME', NULL, NULL, 0);
INSERT INTO public.tags (id, created_at, created_by, description, name, slug, type, updated_at, updated_by, version) VALUES ('019d625e-4f62-7937-b4ca-61139a05347f', '2026-04-06 10:37:21.12267+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, 'Драконы', 'drakony', 'THEME', NULL, NULL, 0);
INSERT INTO public.tags (id, created_at, created_by, description, name, slug, type, updated_at, updated_by, version) VALUES ('019d625e-8763-7245-9e05-f372a83c309c', '2026-04-06 10:37:35.459276+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, 'Компаньоны', 'kompanony', 'THEME', NULL, NULL, 0);
INSERT INTO public.tags (id, created_at, created_by, description, name, slug, type, updated_at, updated_by, version) VALUES ('019d625e-9c68-7fc6-9c2f-95cae9c2b302', '2026-04-06 10:37:40.841071+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, 'Квесты', 'kvesty', 'THEME', NULL, NULL, 0);
INSERT INTO public.tags (id, created_at, created_by, description, name, slug, type, updated_at, updated_by, version) VALUES ('019d625e-b43c-7fc6-97ba-b67e4a2ed64f', '2026-04-06 10:37:46.941063+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, 'Кулинария', 'kulinariya', 'THEME', NULL, NULL, 0);
INSERT INTO public.tags (id, created_at, created_by, description, name, slug, type, updated_at, updated_by, version) VALUES ('019d625e-d62e-7f58-9ecb-5d0c8d5905cc', '2026-04-06 10:37:55.631066+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, 'Подземелья', 'podzemelya', 'THEME', NULL, NULL, 0);
INSERT INTO public.tags (id, created_at, created_by, description, name, slug, type, updated_at, updated_by, version) VALUES ('019d625e-eded-722d-96d7-eed83f04fbf0', '2026-04-06 10:38:01.709211+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, 'Система', 'sistema', 'THEME', NULL, NULL, 0);
INSERT INTO public.tags (id, created_at, created_by, description, name, slug, type, updated_at, updated_by, version) VALUES ('019d625f-0b6c-7039-9e31-061564cd6182', '2026-04-06 10:38:09.260096+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, 'Фермерство', 'fermerstvo', 'THEME', NULL, NULL, 0);
INSERT INTO public.tags (id, created_at, created_by, description, name, slug, type, updated_at, updated_by, version) VALUES ('019d625f-3488-7353-9db8-f3635201783f', '2026-04-06 10:38:19.784296+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, 'История', 'istoriya', 'GENRE', NULL, NULL, 0);
INSERT INTO public.tags (id, created_at, created_by, description, name, slug, type, updated_at, updated_by, version) VALUES ('019d625f-8c8b-7249-9ccc-3993879a5168', '2026-04-06 10:38:42.315353+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, 'Империи', 'imperii', 'THEME', NULL, NULL, 0);
INSERT INTO public.tags (id, created_at, created_by, description, name, slug, type, updated_at, updated_by, version) VALUES ('019d625f-fdbd-7010-8900-0c9d35b54040', '2026-04-06 10:39:11.293193+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, 'Спорт', 'sport', 'GENRE', NULL, NULL, 0);
INSERT INTO public.tags (id, created_at, created_by, description, name, slug, type, updated_at, updated_by, version) VALUES ('019d6260-3067-7e04-85a3-c00dbf6cf363', '2026-04-06 10:39:24.263957+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, 'Полноцветный', 'polnotsvetnyj', 'CONTENT_WARNING', NULL, NULL, 0);
INSERT INTO public.tags (id, created_at, created_by, description, name, slug, type, updated_at, updated_by, version) VALUES ('019d6260-ac17-715c-81e8-39e785ed7acd', '2026-04-06 10:39:55.927198+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, 'Черно-белый', 'cherno-belyj', 'CONTENT_WARNING', NULL, NULL, 0);
INSERT INTO public.tags (id, created_at, created_by, description, name, slug, type, updated_at, updated_by, version) VALUES ('019d6260-dd3b-78d4-8c33-9ad2934eabf7', '2026-04-06 10:40:08.507685+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, 'Вампиры', 'vampiry', 'THEME', NULL, NULL, 0);
INSERT INTO public.tags (id, created_at, created_by, description, name, slug, type, updated_at, updated_by, version) VALUES ('019d6261-3456-749f-921c-2d80c7fbd307', '2026-04-06 10:40:30.806386+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, 'Средневековье', 'srednevekove', 'THEME', NULL, NULL, 0);
INSERT INTO public.tags (id, created_at, created_by, description, name, slug, type, updated_at, updated_by, version) VALUES ('019d6261-50eb-772f-b8ce-c0429b3700db', '2026-04-06 10:40:38.123525+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, 'Стимпанк', 'stimpank', 'THEME', NULL, NULL, 0);
INSERT INTO public.tags (id, created_at, created_by, description, name, slug, type, updated_at, updated_by, version) VALUES ('019d6261-80b2-784d-86fa-cb3a69194060', '2026-04-06 10:40:50.354606+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, 'Арт', 'art', 'GENRE', NULL, NULL, 0);
INSERT INTO public.tags (id, created_at, created_by, description, name, slug, type, updated_at, updated_by, version) VALUES ('019d6262-a22a-7ccc-9c56-90d29c43f516', '2026-04-06 10:42:04.459113+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, 'Алхимия', 'alhimiya', 'THEME', NULL, NULL, 0);
INSERT INTO public.tags (id, created_at, created_by, description, name, slug, type, updated_at, updated_by, version) VALUES ('019d6262-f07c-745e-8a3a-aaad012e4ceb', '2026-04-06 10:42:24.508352+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, 'Армия', 'armiya', 'THEME', NULL, NULL, 0);
INSERT INTO public.tags (id, created_at, created_by, description, name, slug, type, updated_at, updated_by, version) VALUES ('019d6263-0423-7056-b5b5-8f1ecbc26bbd', '2026-04-06 10:42:29.539094+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, 'Военные', 'voennye', 'THEME', NULL, NULL, 0);
INSERT INTO public.tags (id, created_at, created_by, description, name, slug, type, updated_at, updated_by, version) VALUES ('019d6263-1d17-7fca-8789-b824cfbe5725', '2026-04-06 10:42:35.928072+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, 'Будущее', 'budushchee', 'THEME', NULL, NULL, 0);
INSERT INTO public.tags (id, created_at, created_by, description, name, slug, type, updated_at, updated_by, version) VALUES ('019d6263-4eac-707a-a176-da361d0c2509', '2026-04-06 10:42:48.620128+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, 'Меха', 'meha', 'GENRE', NULL, NULL, 0);
INSERT INTO public.tags (id, created_at, created_by, description, name, slug, type, updated_at, updated_by, version) VALUES ('019d6263-998b-780c-81de-2737160d9e06', '2026-04-06 10:43:07.787591+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, 'Научная фантастика', 'nauchnaya-fantastika', 'GENRE', NULL, NULL, 0);
INSERT INTO public.tags (id, created_at, created_by, description, name, slug, type, updated_at, updated_by, version) VALUES ('019d6263-bdf3-7764-b371-7ba3f304825d', '2026-04-06 10:43:17.107532+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, 'Ангелы', 'angely', 'THEME', NULL, NULL, 0);
INSERT INTO public.tags (id, created_at, created_by, description, name, slug, type, updated_at, updated_by, version) VALUES ('019d6264-23e3-75a9-8c1c-c5c856a18797', '2026-04-06 10:43:43.203481+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, 'Боевые искусства', 'boevye-iskusstva', 'GENRE', NULL, NULL, 0);
INSERT INTO public.tags (id, created_at, created_by, description, name, slug, type, updated_at, updated_by, version) VALUES ('019d6264-46b0-720c-9c90-eacbf03f90e1', '2026-04-06 10:43:52.112291+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, 'Супер-сила', 'super-sila', 'GENRE', NULL, NULL, 0);
INSERT INTO public.tags (id, created_at, created_by, description, name, slug, type, updated_at, updated_by, version) VALUES ('019d6264-76a5-7753-aa3f-8c90840691de', '2026-04-06 10:44:04.389529+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, 'Спортивное тело', 'sportivnoe-telo', 'THEME', NULL, NULL, 0);
INSERT INTO public.tags (id, created_at, created_by, description, name, slug, type, updated_at, updated_by, version) VALUES ('019d6264-afc5-7ebc-ab26-a32347ac81ae', '2026-04-06 10:44:19.013996+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, 'Сёдзе', 'sedze', 'GENRE', NULL, NULL, 0);
INSERT INTO public.tags (id, created_at, created_by, description, name, slug, type, updated_at, updated_by, version) VALUES ('019d6264-fa7a-7cf9-99b2-c6fe75def5a7', '2026-04-06 10:44:38.138889+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, 'Брат и сестра', 'brat-i-sestra', 'THEME', NULL, NULL, 0);
INSERT INTO public.tags (id, created_at, created_by, description, name, slug, type, updated_at, updated_by, version) VALUES ('019d6265-2aff-7558-ae5e-0365d07d5957', '2026-04-06 10:44:50.559441+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, 'Боги', 'bogi', 'THEME', NULL, NULL, 0);
INSERT INTO public.tags (id, created_at, created_by, description, name, slug, type, updated_at, updated_by, version) VALUES ('019d6265-5c01-7845-84ca-348bfd3cafd6', '2026-04-06 10:45:03.105598+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, 'Война', 'vojna', 'THEME', NULL, NULL, 0);
INSERT INTO public.tags (id, created_at, created_by, description, name, slug, type, updated_at, updated_by, version) VALUES ('019d6265-760e-7c8f-8fd5-3375fb6e908d', '2026-04-06 10:45:09.774872+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, 'Гяру', 'gyaru', 'THEME', NULL, NULL, 0);
INSERT INTO public.tags (id, created_at, created_by, description, name, slug, type, updated_at, updated_by, version) VALUES ('019d6265-b877-7676-b2df-780e1285dd04', '2026-04-06 10:45:26.775479+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, 'Призраки/духи', 'prizraki-duhi', 'THEME', NULL, NULL, 0);
INSERT INTO public.tags (id, created_at, created_by, description, name, slug, type, updated_at, updated_by, version) VALUES ('019d6265-d20a-7a24-b2b8-95867364dc64', '2026-04-06 10:45:33.322703+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, 'Злые духи', 'zlye-duhi', 'THEME', NULL, NULL, 0);
INSERT INTO public.tags (id, created_at, created_by, description, name, slug, type, updated_at, updated_by, version) VALUES ('019d6266-0256-7d9d-8bb8-54bd50624e0f', '2026-04-06 10:45:45.686925+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, 'Бои на мечах', 'boi-na-mechah', 'THEME', NULL, NULL, 0);
INSERT INTO public.tags (id, created_at, created_by, description, name, slug, type, updated_at, updated_by, version) VALUES ('019d6266-64a9-7620-8266-191e371e92c6', '2026-04-06 10:46:10.85763+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, 'Горничные', 'gornichnye', 'THEME', NULL, NULL, 0);
INSERT INTO public.tags (id, created_at, created_by, description, name, slug, type, updated_at, updated_by, version) VALUES ('019d6266-83b3-7800-a36b-0d49596fa693', '2026-04-06 10:46:18.803596+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, 'Мафия', 'mafiya', 'THEME', NULL, NULL, 0);
INSERT INTO public.tags (id, created_at, created_by, description, name, slug, type, updated_at, updated_by, version) VALUES ('019d6266-9f80-750e-a629-36f8d4d5ac6e', '2026-04-06 10:46:25.920385+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, 'Наёмники', 'naemniki', 'THEME', NULL, NULL, 0);
INSERT INTO public.tags (id, created_at, created_by, description, name, slug, type, updated_at, updated_by, version) VALUES ('019d6267-18b9-7c9b-b56c-79844c920738', '2026-04-06 10:46:56.953867+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, 'Офисные работники', 'ofisnye-rabotniki', 'THEME', NULL, NULL, 0);
INSERT INTO public.tags (id, created_at, created_by, description, name, slug, type, updated_at, updated_by, version) VALUES ('019d6267-2f40-79d2-9456-225c156345a5', '2026-04-06 10:47:02.720688+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, 'Пираты', 'piraty', 'THEME', NULL, NULL, 0);
INSERT INTO public.tags (id, created_at, created_by, description, name, slug, type, updated_at, updated_by, version) VALUES ('019d6267-85c9-7ad0-ab42-adf841f59acf', '2026-04-06 10:47:24.873763+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, 'Эротика', 'erotika', 'GENRE', NULL, NULL, 0);
INSERT INTO public.tags (id, created_at, created_by, description, name, slug, type, updated_at, updated_by, version) VALUES ('019d6267-ae9d-7a62-90af-829c3e32781e', '2026-04-06 10:47:35.325729+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, 'Артефакты', 'artefakty', 'THEME', NULL, NULL, 0);
INSERT INTO public.tags (id, created_at, created_by, description, name, slug, type, updated_at, updated_by, version) VALUES ('019d6267-d74e-7651-98fb-fcce48470c2d', '2026-04-06 10:47:45.742463+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, 'Месть', 'mest', 'THEME', NULL, NULL, 0);
INSERT INTO public.tags (id, created_at, created_by, description, name, slug, type, updated_at, updated_by, version) VALUES ('019d6268-2781-746e-b480-9304b8d7a5ed', '2026-04-06 10:48:06.273431+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, 'Яндере', 'yandere', 'THEME', NULL, NULL, 0);
INSERT INTO public.tags (id, created_at, created_by, description, name, slug, type, updated_at, updated_by, version) VALUES ('019d6268-7c1c-7897-b853-8da10d44309e', '2026-04-06 10:48:27.932625+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, 'Магическая академия', 'magicheskaya-akademiya', 'THEME', NULL, NULL, 0);
INSERT INTO public.tags (id, created_at, created_by, description, name, slug, type, updated_at, updated_by, version) VALUES ('019d6268-a2c9-7a5e-84e7-ff239e804f19', '2026-04-06 10:48:37.833742+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, 'Ранги силы', 'rangi-sily', 'THEME', NULL, NULL, 0);
INSERT INTO public.tags (id, created_at, created_by, description, name, slug, type, updated_at, updated_by, version) VALUES ('019d6268-bbb7-739d-b790-f88a406c9b33', '2026-04-06 10:48:44.215304+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, 'Учитель', 'uchitel', 'THEME', NULL, NULL, 0);
INSERT INTO public.tags (id, created_at, created_by, description, name, slug, type, updated_at, updated_by, version) VALUES ('019d6268-d260-700c-bf7a-d106ea219b06', '2026-04-06 10:48:50.016086+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, 'Япония', 'yaponiya', 'THEME', NULL, NULL, 0);


ALTER TABLE public.tags ENABLE TRIGGER ALL;

--
-- authors
--

ALTER TABLE public.authors DISABLE TRIGGER ALL;

INSERT INTO public.authors (id, country_iso_code, created_at, created_by, description, main_cover_media_id, name, slug, updated_at, updated_by, version, website_urls) VALUES ('019d624f-62c6-7472-9724-12e0d90ab1cf', 'JP', '2026-04-06 10:21:03.048962+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, NULL, 'Тацуки Фудзимото', 'tatsuki-fudzimoto', NULL, NULL, 0, '{}');
INSERT INTO public.authors (id, country_iso_code, created_at, created_by, description, main_cover_media_id, name, slug, updated_at, updated_by, version, website_urls) VALUES ('019d626c-a68c-7385-b494-b4bdb6361322', 'KR', '2026-04-06 10:53:00.940674+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, NULL, 'Ким Кханби', 'kim-khanbi', NULL, NULL, 0, '{}');
INSERT INTO public.authors (id, country_iso_code, created_at, created_by, description, main_cover_media_id, name, slug, updated_at, updated_by, version, website_urls) VALUES ('019d626c-ef19-71fb-a4bb-e86547aa6a32', 'KR', '2026-04-06 10:53:19.513216+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, NULL, 'Hwang Youngchan', 'hwang-youngchan', NULL, NULL, 0, '{}');
INSERT INTO public.authors (id, country_iso_code, created_at, created_by, description, main_cover_media_id, name, slug, updated_at, updated_by, version, website_urls) VALUES ('019d626f-524e-749f-8468-d1ce33566ab7', 'JP', '2026-04-06 10:55:55.982451+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, NULL, 'Кидо Джироу', 'kido-dzhirou', NULL, NULL, 0, '{}');
INSERT INTO public.authors (id, country_iso_code, created_at, created_by, description, main_cover_media_id, name, slug, updated_at, updated_by, version, website_urls) VALUES ('019d626f-927d-7a08-8bab-1d9a042b77ba', 'KR', '2026-04-06 10:56:12.413708+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, NULL, 'Brother Lim', 'brother-lim', NULL, NULL, 0, '{}');
INSERT INTO public.authors (id, country_iso_code, created_at, created_by, description, main_cover_media_id, name, slug, updated_at, updated_by, version, website_urls) VALUES ('019d626f-b17b-7564-9dfb-bbed2852d532', 'KR', '2026-04-06 10:56:20.347423+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, NULL, 'Lee Hae kyung', 'lee-hae-kyung', NULL, NULL, 0, '{}');
INSERT INTO public.authors (id, country_iso_code, created_at, created_by, description, main_cover_media_id, name, slug, updated_at, updated_by, version, website_urls) VALUES ('019d6270-00b6-7da5-9291-549fd5badcec', 'JP', '2026-04-06 10:56:40.630917+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, NULL, 'Рёсукэ Такэути', 'resuke-takeuti', NULL, NULL, 0, '{}');
INSERT INTO public.authors (id, country_iso_code, created_at, created_by, description, main_cover_media_id, name, slug, updated_at, updated_by, version, website_urls) VALUES ('019d6270-4259-7697-b53e-9bc8e7ae3a66', NULL, '2026-04-06 10:56:57.43373+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, NULL, 'Miyoshi Hikaru', 'miyoshi-hikaru', NULL, NULL, 0, '{}');
INSERT INTO public.authors (id, country_iso_code, created_at, created_by, description, main_cover_media_id, name, slug, updated_at, updated_by, version, website_urls) VALUES ('019d6270-f820-758d-94a7-0574d0c58b61', NULL, '2026-04-06 10:57:43.968426+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, NULL, 'JO Yongseok', 'jo-yongseok', NULL, NULL, 0, '{}');
INSERT INTO public.authors (id, country_iso_code, created_at, created_by, description, main_cover_media_id, name, slug, updated_at, updated_by, version, website_urls) VALUES ('019d6271-1565-7a5e-a4d2-2faabc49ee0c', NULL, '2026-04-06 10:57:51.461727+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, NULL, 'MOCHIZUKI Jun', 'mochizuki-jun', NULL, NULL, 0, '{}');
INSERT INTO public.authors (id, country_iso_code, created_at, created_by, description, main_cover_media_id, name, slug, updated_at, updated_by, version, website_urls) VALUES ('019d6271-4af5-7358-b7f7-ed673ca67066', NULL, '2026-04-06 10:58:05.173299+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, NULL, 'Юн Ин Ван', 'yun-in-van', NULL, NULL, 0, '{}');
INSERT INTO public.authors (id, country_iso_code, created_at, created_by, description, main_cover_media_id, name, slug, updated_at, updated_by, version, website_urls) VALUES ('019d6271-6e54-7a6e-981b-16eff75ebfae', NULL, '2026-04-06 10:58:14.228737+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, NULL, 'Ким Сун Хи', 'kim-sun-hi', NULL, NULL, 0, '{}');
INSERT INTO public.authors (id, country_iso_code, created_at, created_by, description, main_cover_media_id, name, slug, updated_at, updated_by, version, website_urls) VALUES ('019d6271-ab38-72cc-98eb-2d4c80aed1fa', NULL, '2026-04-06 10:58:29.816258+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, NULL, 'Ёсиюки Садамото', 'esiyuki-sadamoto', NULL, NULL, 0, '{}');
INSERT INTO public.authors (id, country_iso_code, created_at, created_by, description, main_cover_media_id, name, slug, updated_at, updated_by, version, website_urls) VALUES ('019d6272-06f1-7d6c-ba26-d052dd279bfd', NULL, '2026-04-06 10:58:53.297906+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, NULL, 'Пак Тхэ Джун', 'pak-the-dzhun', NULL, NULL, 0, '{}');
INSERT INTO public.authors (id, country_iso_code, created_at, created_by, description, main_cover_media_id, name, slug, updated_at, updated_by, version, website_urls) VALUES ('019d6272-4296-79a5-a652-71199717b80e', NULL, '2026-04-06 10:59:08.566847+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, NULL, 'HON', 'hon', NULL, NULL, 0, '{}');
INSERT INTO public.authors (id, country_iso_code, created_at, created_by, description, main_cover_media_id, name, slug, updated_at, updated_by, version, website_urls) VALUES ('019d6272-7e9b-717c-aaa8-4d9c7a6baa51', NULL, '2026-04-06 10:59:23.931153+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, NULL, 'HERO', 'hero', NULL, NULL, 0, '{}');
INSERT INTO public.authors (id, country_iso_code, created_at, created_by, description, main_cover_media_id, name, slug, updated_at, updated_by, version, website_urls) VALUES ('019d6272-c0f4-7ced-a544-18ff845e1113', NULL, '2026-04-06 10:59:40.916864+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, NULL, 'Тайто Кубо', 'tajto-kubo', NULL, NULL, 0, '{}');
INSERT INTO public.authors (id, country_iso_code, created_at, created_by, description, main_cover_media_id, name, slug, updated_at, updated_by, version, website_urls) VALUES ('019d6272-fc42-7b33-bdd5-6361888c3bd5', NULL, '2026-04-06 10:59:56.098766+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, NULL, 'Рэй Хироэ', 'rej-hiroe', NULL, NULL, 0, '{}');
INSERT INTO public.authors (id, country_iso_code, created_at, created_by, description, main_cover_media_id, name, slug, updated_at, updated_by, version, website_urls) VALUES ('019d6273-4c65-7841-a7b3-a473a30a00b0', NULL, '2026-04-06 11:00:16.613588+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, NULL, 'MORI Kouji', 'mori-kouji', NULL, NULL, 0, '{}');
INSERT INTO public.authors (id, country_iso_code, created_at, created_by, description, main_cover_media_id, name, slug, updated_at, updated_by, version, website_urls) VALUES ('019d6273-8452-7814-bcf2-eccbc3d078d7', NULL, '2026-04-06 11:00:30.930559+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, NULL, 'Кэнтаро Миура', 'kentaro-miura', NULL, NULL, 0, '{}');
INSERT INTO public.authors (id, country_iso_code, created_at, created_by, description, main_cover_media_id, name, slug, updated_at, updated_by, version, website_urls) VALUES ('019d6273-b58e-72d4-adfa-135f98d3d660', NULL, '2026-04-06 11:00:43.534267+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, NULL, 'Hinata Nakamura', 'hinata-nakamura', NULL, NULL, 0, '{}');
INSERT INTO public.authors (id, country_iso_code, created_at, created_by, description, main_cover_media_id, name, slug, updated_at, updated_by, version, website_urls) VALUES ('019d6273-df05-749f-a178-9111f39bd4d7', NULL, '2026-04-06 11:00:54.1494+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, NULL, 'Ли И Рон', 'li-i-ron', NULL, NULL, 0, '{}');
INSERT INTO public.authors (id, country_iso_code, created_at, created_by, description, main_cover_media_id, name, slug, updated_at, updated_by, version, website_urls) VALUES ('019d6274-1ac9-70fd-9a74-f98c84b17d4e', NULL, '2026-04-06 11:01:09.44913+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, NULL, 'Гэгэ Акутами', 'gege-akutami', NULL, NULL, 0, '{}');
INSERT INTO public.authors (id, country_iso_code, created_at, created_by, description, main_cover_media_id, name, slug, updated_at, updated_by, version, website_urls) VALUES ('019d6274-8fec-770e-a85e-f06e6f6b4aae', NULL, '2026-04-06 11:01:39.436558+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, NULL, 'Хон Пиль', 'hon-pil', NULL, NULL, 0, '{}');
INSERT INTO public.authors (id, country_iso_code, created_at, created_by, description, main_cover_media_id, name, slug, updated_at, updated_by, version, website_urls) VALUES ('019d62a0-206c-7316-b300-e9b7f8588184', 'JP', '2026-04-06 11:49:14.476282+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', 'Исаяма родился 29 августа 1986 года в Ояме, Оита, Япония.

Окончив среднюю школу Хитаринкоу префектуры Оита, он поступил в программу мультяшного дизайна факультета мультяшных искусств, дизайн.', NULL, 'Хадзимэ Исаяма', 'hadzime-isayama', NULL, NULL, 0, '{}');


ALTER TABLE public.authors ENABLE TRIGGER ALL;

--
-- publishers
--

ALTER TABLE public.publishers DISABLE TRIGGER ALL;

INSERT INTO public.publishers (id, country_iso_code, created_at, created_by, description, logo_media_id, name, slug, updated_at, updated_by, version, website_urls) VALUES ('019d626b-6ada-70f1-983f-efae19d72159', 'JP', '2026-04-06 10:51:40.128772+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, NULL, 'Shueisha', 'shueisha', NULL, NULL, 0, '{https://www.shueisha.co.jp,https://mangaplus.shueisha.co.jp}');
INSERT INTO public.publishers (id, country_iso_code, created_at, created_by, description, logo_media_id, name, slug, updated_at, updated_by, version, website_urls) VALUES ('019d626d-985a-7743-8607-75caf63d47b3', NULL, '2026-04-06 10:54:02.842551+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, NULL, 'Naver', 'naver', NULL, NULL, 0, '{https://comic.naver.com/index}');
INSERT INTO public.publishers (id, country_iso_code, created_at, created_by, description, logo_media_id, name, slug, updated_at, updated_by, version, website_urls) VALUES ('019d626e-234b-73ba-8582-290fcd4d0893', 'KR', '2026-04-06 10:54:38.411298+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, NULL, 'Webtoon', 'webtoon', NULL, NULL, 0, '{https://www.webtoons.com/en/}');
INSERT INTO public.publishers (id, country_iso_code, created_at, created_by, description, logo_media_id, name, slug, updated_at, updated_by, version, website_urls) VALUES ('019d626e-9e7d-7b33-b343-7182862831d5', 'RU', '2026-04-06 10:55:09.949805+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, NULL, 'Азбука', 'azbuka', NULL, NULL, 0, '{}');
INSERT INTO public.publishers (id, country_iso_code, created_at, created_by, description, logo_media_id, name, slug, updated_at, updated_by, version, website_urls) VALUES ('019d6274-cb7c-794b-878c-b5f80d125e2d', NULL, '2026-04-06 11:01:54.684753+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, NULL, 'Viz', 'viz', NULL, NULL, 0, '{}');
INSERT INTO public.publishers (id, country_iso_code, created_at, created_by, description, logo_media_id, name, slug, updated_at, updated_by, version, website_urls) VALUES ('019d6274-f5c5-7b74-af3a-6bf4090cb58e', NULL, '2026-04-06 11:02:05.509791+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, NULL, 'Square Enix', 'square-enix', NULL, NULL, 0, '{}');
INSERT INTO public.publishers (id, country_iso_code, created_at, created_by, description, logo_media_id, name, slug, updated_at, updated_by, version, website_urls) VALUES ('019d6275-2ee4-7235-b6d7-69b18b8078ac', NULL, '2026-04-06 11:02:20.132195+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, NULL, 'Istari Comix', 'istari-comix', NULL, NULL, 0, '{}');
INSERT INTO public.publishers (id, country_iso_code, created_at, created_by, description, logo_media_id, name, slug, updated_at, updated_by, version, website_urls) VALUES ('019d6275-5e87-7306-b077-ff36ff72282d', NULL, '2026-04-06 11:02:32.327434+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, NULL, 'YLab', 'ylab', NULL, NULL, 0, '{}');
INSERT INTO public.publishers (id, country_iso_code, created_at, created_by, description, logo_media_id, name, slug, updated_at, updated_by, version, website_urls) VALUES ('019d6275-8bf9-7f3b-9a3e-958d709c43cc', NULL, '2026-04-06 11:02:43.962036+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, NULL, 'D&C Media', 'd-c-media', NULL, NULL, 0, '{}');
INSERT INTO public.publishers (id, country_iso_code, created_at, created_by, description, logo_media_id, name, slug, updated_at, updated_by, version, website_urls) VALUES ('019d6275-c201-7c41-9260-b61def6c32bd', 'JP', '2026-04-06 11:02:57.793849+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, NULL, 'Kakao', 'kakao', NULL, NULL, 0, '{}');
INSERT INTO public.publishers (id, country_iso_code, created_at, created_by, description, logo_media_id, name, slug, updated_at, updated_by, version, website_urls) VALUES ('019d6276-073c-7f43-b809-2ce95c97787d', NULL, '2026-04-06 11:03:15.517046+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, NULL, 'Bilibili Comics', 'bilibili-comics', NULL, NULL, 0, '{}');
INSERT INTO public.publishers (id, country_iso_code, created_at, created_by, description, logo_media_id, name, slug, updated_at, updated_by, version, website_urls) VALUES ('019d6276-2202-7ec4-b606-cb27c9d76fa0', NULL, '2026-04-06 11:03:22.371066+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, NULL, 'Yen Press', 'yen-press', NULL, NULL, 0, '{}');
INSERT INTO public.publishers (id, country_iso_code, created_at, created_by, description, logo_media_id, name, slug, updated_at, updated_by, version, website_urls) VALUES ('019d6276-47d4-7a62-83c9-520e49605b82', NULL, '2026-04-06 11:03:32.052753+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, NULL, 'Эксмо', 'eksmo', NULL, NULL, 0, '{}');
INSERT INTO public.publishers (id, country_iso_code, created_at, created_by, description, logo_media_id, name, slug, updated_at, updated_by, version, website_urls) VALUES ('019d6276-7b4a-721c-a129-4aa3b021b0c0', NULL, '2026-04-06 11:03:45.226284+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, NULL, 'Shogakukan', 'shogakukan', NULL, NULL, 0, '{}');
INSERT INTO public.publishers (id, country_iso_code, created_at, created_by, description, logo_media_id, name, slug, updated_at, updated_by, version, website_urls) VALUES ('019d6276-b523-76fd-9cb3-c30b5fd91a46', NULL, '2026-04-06 11:04:00.035505+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, NULL, 'Hakusensha', 'hakusensha', NULL, NULL, 0, '{}');
INSERT INTO public.publishers (id, country_iso_code, created_at, created_by, description, logo_media_id, name, slug, updated_at, updated_by, version, website_urls) VALUES ('019d6276-d42c-7cb4-aed2-e40b5450c225', NULL, '2026-04-06 11:04:07.980881+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, NULL, 'Dark Horse Comics', 'dark-horse-comics', NULL, NULL, 0, '{}');
INSERT INTO public.publishers (id, country_iso_code, created_at, created_by, description, logo_media_id, name, slug, updated_at, updated_by, version, website_urls) VALUES ('019d6276-f416-7a87-bb21-4c66c76e97a2', NULL, '2026-04-06 11:04:16.150732+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, NULL, 'XL Media', 'xl-media', NULL, NULL, 0, '{}');
INSERT INTO public.publishers (id, country_iso_code, created_at, created_by, description, logo_media_id, name, slug, updated_at, updated_by, version, website_urls) VALUES ('019d6278-ba41-782d-a25e-5032f8e6281e', 'KR', '2026-04-06 11:06:12.417587+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', 'Портал, основанный южнокорейским блогером Хан Хи Соном в 2013 году, для распространения вебтунов. Он базируется в Южной Корее, а его услуги предлагаются на корейском, японском и английском языках.', NULL, 'Lezhin Comics', 'lezhin-comics', NULL, NULL, 0, '{https://www.lezhin.com/ko}');
INSERT INTO public.publishers (id, country_iso_code, created_at, created_by, description, logo_media_id, name, slug, updated_at, updated_by, version, website_urls) VALUES ('019d629f-b48d-772b-989e-02f2b03ff6e5', 'JP', '2026-04-06 11:48:46.861575+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', NULL, NULL, 'Kodansha', 'kodansha', NULL, NULL, 0, '{https://kodansha.us/,https://www.kodansha.co.jp/}');


ALTER TABLE public.publishers ENABLE TRIGGER ALL;

--
-- titles
--

ALTER TABLE public.titles DISABLE TRIGGER ALL;

INSERT INTO public.titles (id, content_rating, country_iso_code, created_at, created_by, description, is_licensed, main_cover_media_id, name, release_year, slug, title_status, type, updated_at, updated_by, version) VALUES ('019d627b-6900-77ef-a752-f474952fc14b', 'EIGHTEEN_PLUS', 'JP', '2026-04-06 11:09:08.227304+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', '«Я всегда мечтал жить обычной жизнью: спать в тёплой постели, есть тосты с джемом по утрам, ходить на свидания со своей девушкой и улыбаться каждый день. Но всё изменилось со смертью отца — теперь, Потита, пора убивать!» — с такими словами Дэндзи, вместе со своим псом-бензопилой, Потитой, отправляется на очередное задание, ведь они — охотники на демонов. Каждый день они убивают ради денег, которые Дэндзи должен отдать одному якудзе, иначе долг покойного отца придётся отдать собственной жизнью. Но что ждёт Дэндзи, когда он вернёт весь долг: заживёт обычной жизнью или продолжит спасать мир от демонов? А может, у судьбы свои планы на участь героя?', false, NULL, 'Человек-бензопила', 2018, 'chelovek-benzopila', 'COMPLETED', 'MANGA', NULL, NULL, 0);
INSERT INTO public.titles (id, content_rating, country_iso_code, created_at, created_by, description, is_licensed, main_cover_media_id, name, release_year, slug, title_status, type, updated_at, updated_by, version) VALUES ('019d627c-fb71-7cd0-8825-3231a54d9dcb', 'SIXTEEN_PLUS', 'JP', '2026-04-06 11:10:51.24986+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', 'Мать Юты смертельно больна. Она попросила запечатлеть её последние дни на только что подаренный сыну телефон. Из отснятого материала Юта смонтировал фильм, который показал на школьном фестивале. Его старания не приняли, и парень пришёл на крышу больницы, чтобы покончить с собой, но там встретил Эри, которая заявила, что фильм ей понравился и она хочет увидеть ещё одну работу Юты.', false, NULL, 'Прощай, Эри', 2022, 'proshchaj-eri', 'COMPLETED', 'MANGA', NULL, NULL, 0);
INSERT INTO public.titles (id, content_rating, country_iso_code, created_at, created_by, description, is_licensed, main_cover_media_id, name, release_year, slug, title_status, type, updated_at, updated_by, version) VALUES ('019d627e-a622-7c00-8927-7d86709b983e', 'SIXTEEN_PLUS', 'JP', '2026-04-06 11:12:40.482824+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', 'Фудзино — четвероклассница, которая рисует ёнкомы для школьной газеты. Рисунки делают её звездой класса, но однажды ей говорят, что Кёмото — ученица, которая отказывается ходить в школу — тоже хочет нарисовать мангу для школьной газеты...', false, NULL, 'Оглянись', 2021, 'oglyanis', 'COMPLETED', 'MANGA', NULL, NULL, 0);
INSERT INTO public.titles (id, content_rating, country_iso_code, created_at, created_by, description, is_licensed, main_cover_media_id, name, release_year, slug, title_status, type, updated_at, updated_by, version) VALUES ('019d6281-fda0-7649-8606-3eb3ef21f5d5', 'EIGHTEEN_PLUS', 'KR', '2026-04-06 11:16:19.488488+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', 'Твоя задротская душа
Не любит всех — только себя.
Ты шлёшь семью и мир вокруг,
И без друзей твой ближний круг.
Но как бывает иногда,
Лишь потеряв всё навсегда,
Решил ты небо не коптить
И суицид взять совершить.
Но мир на блажь твою плевал
И кучу ужасов наслал.
Теперь придётся как-то жить...
Ну и всех монстров победить?', false, NULL, 'Милый дом', 2017, 'milyj-dom', 'COMPLETED', 'MANHWA', NULL, NULL, 0);
INSERT INTO public.titles (id, content_rating, country_iso_code, created_at, created_by, description, is_licensed, main_cover_media_id, name, release_year, slug, title_status, type, updated_at, updated_by, version) VALUES ('019d6286-8d66-76b8-87aa-34307fe9c34b', 'SIXTEEN_PLUS', 'KR', '2026-04-06 11:21:18.438491+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', 'Джин Сон нигде не может найти утешения. В школе над ним безжалостно издеваются из-за его замкнутого характера и слабого здоровья. Однако не это является источником непреодолимого ужаса Джина: то, что он боится больше всего, — это его собственный отец.
Для большинства отец Джина — отзывчивый человек, успешный бизнесмен и любящий родитель. Но это только видимость. Правда в том, что он — сумасшедший серийный убийца, а Джин — его невольный соучастник. В течение многих лет Джину приходилось, вводя в заблуждение полицию, помогать отцу. Однако, когда его отец начинает проявлять интерес к ученице по обмену Кён Юн, Джин должен принять решение — быть трусом, который отправит её на виселицу, как всех остальных, или станет ублюдком, который бросит вызов своему отцу...', false, NULL, 'Сволочь', 2014, 'svoloch', 'COMPLETED', 'MANHWA', NULL, NULL, 0);
INSERT INTO public.titles (id, content_rating, country_iso_code, created_at, created_by, description, is_licensed, main_cover_media_id, name, release_year, slug, title_status, type, updated_at, updated_by, version) VALUES ('019d6288-ea99-7cf9-8047-cf1012f226bb', 'EIGHTEEN_PLUS', 'KR', '2026-04-06 11:23:53.369912+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', 'Расслабляющий отдых в раю или смертельная ловушка? Главный герой просыпается на захватывающем дух пляже, но понятия не имеет, кто он и как сюда попал. Как бы он ни пытался собрать всё воедино, распутать эту головокружительную тайну будет нелегко, когда каждая подсказка ведёт к ещё более безумным вопросам... и семья, которая приветствует его в своем доме, не такая, какой кажется.', false, '019d6288-4f16-7b78-ae05-154b9cb3f895', 'Свинарник', 2019, 'svinarnik', 'COMPLETED', 'MANHWA', NULL, NULL, 0);
INSERT INTO public.titles (id, content_rating, country_iso_code, created_at, created_by, description, is_licensed, main_cover_media_id, name, release_year, slug, title_status, type, updated_at, updated_by, version) VALUES ('019d628e-6ba6-76b0-a45e-a925f7b5f100', 'SIXTEEN_PLUS', 'KR', '2026-04-06 11:29:54.086484+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', 'Быть неудачником — клеймо,
И дали б чёртово ружьё,
Ты б застрелил всех подлецов,
Всех мразей, трусов, гордецов...

Но ты — слабак, а ночь темна
И монстрами она окружена.
И в руки получив ружьё,
На кого направишь ты его?', false, NULL, 'Мальчик с ружьём', 2021, 'malchik-s-ruzhem', 'COMPLETED', 'MANHWA', NULL, NULL, 0);
INSERT INTO public.titles (id, content_rating, country_iso_code, created_at, created_by, description, is_licensed, main_cover_media_id, name, release_year, slug, title_status, type, updated_at, updated_by, version) VALUES ('019d6290-58cb-7efd-a92e-f588d8584763', 'SIXTEEN_PLUS', 'JP', '2026-04-06 11:32:00.332009+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', 'Это мир манги, которая получает 1 страницу раз в год!', false, NULL, 'Мир манги, который получает одну страницу раз в год', 2019, 'mir-mangi-kotoryj-poluchaet-odnu-stranitsu-raz-v-god', 'SUSPENDED', 'MANGA', NULL, NULL, 0);
INSERT INTO public.titles (id, content_rating, country_iso_code, created_at, created_by, description, is_licensed, main_cover_media_id, name, release_year, slug, title_status, type, updated_at, updated_by, version) VALUES ('019d6292-643b-7158-bdfa-0675ac233327', 'SIXTEEN_PLUS', 'KR', '2026-04-06 11:34:14.331563+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', 'Внезапно в один прекрасный день в городе появилась таинственная башня. Люди решили назвать её подземельем, и хоть внутри находилась сплошная пересеченная местность с ордой опасных монстров, она была также и землей возможностей, где находились несметные сокровища. Седжун, молодой человек, ведущий повседневную жизнь, был очень взволнован, когда неожиданно получил приглашение в подземелье. По приглашению он оказывается в ловушке в секретной зоне таинственной башни. У него есть только его тело и несколько семян. Теперь Седжуну предстоит выращивать урожай, собирать припасы и разрабатывать свой собственный план выживания!', false, NULL, 'Фермерство в башне в одиночку', 2023, 'fermerstvo-v-bashne-v-odinochku', 'ONGOING', 'MANHWA', NULL, NULL, 0);
INSERT INTO public.titles (id, content_rating, country_iso_code, created_at, created_by, description, is_licensed, main_cover_media_id, name, release_year, slug, title_status, type, updated_at, updated_by, version) VALUES ('019d6295-9378-72b0-b05e-a1d7ce67b029', 'SIXTEEN_PLUS', 'JP', '2026-04-06 11:37:43.032249+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', 'Конец позапрошлого века, Британская империя правит миром, и в основе всего лежит классовая система. И тут внезапно появляется Уильям Джеймс Мориарти — молодой человек, желающий уничтожить зло, проистекающее из классовой дискриминации, и создать идеальную страну.', false, NULL, 'Патриотизм Мориарти', 2016, 'patriotizm-moriarti', 'ONGOING', 'MANGA', NULL, NULL, 0);
INSERT INTO public.titles (id, content_rating, country_iso_code, created_at, created_by, description, is_licensed, main_cover_media_id, name, release_year, slug, title_status, type, updated_at, updated_by, version) VALUES ('019d6297-69f8-7de3-b468-6b1057989e9f', 'EIGHTEEN_PLUS', 'KR', '2026-04-06 11:39:43.480941+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', 'Манхва «Ветролом» — драма о юных уличных гонщиках, мечтающих о свободе.

Главный герой Джахён Чо — сын успешных родителей и лучший ученик в своей школе. Он никогда не знал, что значит «бороться за свою мечту», ведь вся его жизнь определялась родителями, которые хотят для сына только одного — успешного окончания школы.
Но однажды Джахён, с детства любящий велоспорт, оказывается втянут в деятельность местных гонщиков, что заставляет героя пересмотреть приоритеты родителей и прислушаться к себе, ведь на своём новом пути он обретает друзей, любовь и приключения.', false, NULL, 'Ветролом', 2013, 'vetrolom', 'COMPLETED', 'MANHWA', NULL, NULL, 0);
INSERT INTO public.titles (id, content_rating, country_iso_code, created_at, created_by, description, is_licensed, main_cover_media_id, name, release_year, slug, title_status, type, updated_at, updated_by, version) VALUES ('019d6299-7e9f-7116-98d8-962e29e38ec3', 'EIGHTEEN_PLUS', 'JP', '2026-04-06 11:41:59.839126+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', 'Вы когда-нибудь слышали её? Историю о заводной книге заклинаний, которая щедро одаривает вампиров. Синяя кожаная обложка, блестящие чёрные страницы... Книга заклинаний с часовым механизмом на серебряной цепи... Это была особая книга, созданная Ванитасом, пропитанная мощью, способная управлять истинными именами вампиров. Сейчас, ведомые «Письменами Ванитаса», действующие лица собираются в Париже.', false, NULL, 'Мемуары Ванитаса', 2015, 'memuary-vanitasa', 'ONGOING', 'MANGA', NULL, NULL, 0);
INSERT INTO public.titles (id, content_rating, country_iso_code, created_at, created_by, description, is_licensed, main_cover_media_id, name, release_year, slug, title_status, type, updated_at, updated_by, version) VALUES ('019d629d-1408-7e31-9fa6-72a58ef01d4d', 'SIXTEEN_PLUS', 'KR', '2026-04-06 11:45:54.696954+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', 'Хотя Чичи на вид безобидна, у неё есть цель: захватить мир и уничтожить человечество! Она решает найти товарищей, но, похоже, завоевать мир будет не так легко?! Следите за приключениями тирана Чичи, Нинаю, пёсика, любящего людей, и Кайзера Канга Таешика, дикого и крутого, как яйца, хомяка, и трудностями, с которыми они столкнутся на своём пути к абсолютному господству!', false, NULL, 'Коты, владеющие миром', 2020, 'koty-vladeyushchie-mirom', 'COMPLETED', 'MANHWA', NULL, NULL, 0);
INSERT INTO public.titles (id, content_rating, country_iso_code, created_at, created_by, description, is_licensed, main_cover_media_id, name, release_year, slug, title_status, type, updated_at, updated_by, version) VALUES ('019d629e-3a4b-7d74-bedd-8520badc644b', 'SIXTEEN_PLUS', 'JP', '2026-04-06 11:47:10.027909+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', 'История о девушке из цветочного магазина и студенте, изучающем растения', false, NULL, 'Цветы, влекомые смертью', 2020, 'tsvety-vlekomye-smertyu', 'COMPLETED', 'MANGA', NULL, NULL, 0);
INSERT INTO public.titles (id, content_rating, country_iso_code, created_at, created_by, description, is_licensed, main_cover_media_id, name, release_year, slug, title_status, type, updated_at, updated_by, version) VALUES ('019d6642-18a2-7db6-b7d5-8f7f9f5f375e', 'EIGHTEEN_PLUS', 'KR', '2026-04-07 04:45:00.965123+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', 'Район Каннам погрузился во тьму... «Выжил только я один? Что здесь вообще происходит?..» Парень, проснувшись, обнаружил, что он совсем один в здании, полном трупов. Ни машин, ни электричества и ни одной звезды на небе. Не только Каннам, но и весь город кажется пустым!', false, NULL, 'Далёкое небо', 2014, 'dalekoe-nebo', 'COMPLETED', 'MANHWA', NULL, NULL, 0);
INSERT INTO public.titles (id, content_rating, country_iso_code, created_at, created_by, description, is_licensed, main_cover_media_id, name, release_year, slug, title_status, type, updated_at, updated_by, version) VALUES ('019d664e-3f46-7e7e-a97d-7ae0f15601ab', 'TWELVE_PLUS', 'JP', '2026-04-07 04:58:17.287024+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', 'Хори пытается казаться обычной старшеклассницей, тогда как на самом деле уделяет все свое время уходу за домом. Из-за постоянного отсутствия родителей-трудоголиков, девушке приходится заменять младшему брату семью, заниматься уборкой, стиркой и прочими домашними делами. Однажды она встречает человека, который, так же как и она, старается не открывать свою настоящую сторону личности в школе: Миямура — молчаливый парень в очках. Теперь им двоим есть с кем поделиться и раскрыть настоящих себя, не боясь, что об этом узнают в школе.', false, NULL, 'Хоримия', 2011, 'horimiya', 'COMPLETED', 'MANGA', NULL, NULL, 0);
INSERT INTO public.titles (id, content_rating, country_iso_code, created_at, created_by, description, is_licensed, main_cover_media_id, name, release_year, slug, title_status, type, updated_at, updated_by, version) VALUES ('019d6646-1701-71e7-91cf-4936b494d9f7', 'TWELVE_PLUS', 'JP', '2026-04-07 04:49:22.689363+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', 'История происходит через 15 лет после событий «Второго удара», в результате которого погибло миллиарды людей на всей планете, люди находились на грани выживания. Но тех, кто уцелел, ожидали испытания куда страшнее… Земля подвергается нападению огромных неведомых существ, именуемых – «Ангелами», которые появились непонятно откуда. Единственным оружием планеты, способным их остановить, являются человекоподобные роботы – "Евангелионы", разработанные с помощью новых технологий. Однако, вступать в бой при помощи этих гигантов, могут лишь 14-летние дети, которые способны целиком «подстроить» себя под машину и манипулировать "Евангелионом", подобно своему телу. Эти необыкновенные дети живут и обучаются в городе Токио-3, в штабе "NERV" – организации, которая возглавляет оборону Планеты от нападения. У пилотов Рей, Аски и Синдзи крайне сложно складываются отношения. От них зависит судьба человечества и только они способны найти ответ на вопрос: кто такие «Ангелы».', false, NULL, 'Евангелион', 1994, 'evangelion', 'ONGOING', 'MANGA', NULL, NULL, 0);
INSERT INTO public.titles (id, content_rating, country_iso_code, created_at, created_by, description, is_licensed, main_cover_media_id, name, release_year, slug, title_status, type, updated_at, updated_by, version) VALUES ('019d664c-3048-7ec8-9092-33a5bfa2e515', 'SIXTEEN_PLUS', 'KR', '2026-04-07 04:56:02.377045+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', 'Пак Хён Сок — непривлекательный парень с избыточным весом, подвергающийся школьной травле. Он знает всё о беспомощности, страхе и несправедливости. Но однажды, решив начать новую жизнь, он вдруг обретает и новое тело. Как же поведёт себя извечный неудачник, получив небывалые силу и красоту, и впервые столкнувшись с восхищением окружающих? Докопается ли он до секретов внезапного преображения? И построит ли по-настоящему нового себя?..', false, NULL, 'Лукизм', 2014, 'lukizm', 'ONGOING', 'MANHWA', NULL, NULL, 0);
INSERT INTO public.titles (id, content_rating, country_iso_code, created_at, created_by, description, is_licensed, main_cover_media_id, name, release_year, slug, title_status, type, updated_at, updated_by, version) VALUES ('019d6650-4e69-7fc2-bdf5-2a18fca27084', 'SIXTEEN_PLUS', 'JP', '2026-04-07 05:00:32.234067+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', 'Ичиго Куросаки с детства мог видеть призраков и духов умерших. Сейчас ему пятнадцать, живёт он в современной Японии и учится в старшей школе.
В один ничем не примечательный день в его спальне внезапно объявляется девушка — Рукия Кучики. Она синигами и сильно удивлена тем, что Ичиго может не только видеть её, но и, неслыханное дело, касаться. Начавшееся было выяснение отношений прерывается появлением Пустого, того самого, за которым и явилась Рукия. Бой с Пустым складывается неудачно: вынужденную защищать Ичиго, Рукию серьёзно ранят. Ради спасения Ичиго она передаёт ему часть собственных способностей, но, к её удивлению, Ичиго поглощает всю её энергию и сам становится синигами. Оставив Рукию без сил и практически беспомощной, Ичиго с лёгкостью одолевает Пустого.', false, NULL, 'Блич', 2001, 'blich', 'COMPLETED', 'MANGA', NULL, NULL, 0);
INSERT INTO public.titles (id, content_rating, country_iso_code, created_at, created_by, description, is_licensed, main_cover_media_id, name, release_year, slug, title_status, type, updated_at, updated_by, version) VALUES ('019d6652-fbcd-7e8b-9609-d27f7a8822de', 'EIGHTEEN_PLUS', 'JP', '2026-04-07 05:03:27.69399+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', 'История поведает о команде наёмников, известных как «Компания Лагуна», занимающихся незаконным ввозом товаров вокруг морей Юго-Восточной Азии. Их база расположена в вымышленном городе Роанапура в Таиланде, и они перевозят грузы в торпедном катере «Чёрная Лагуна». «Компания Лагуна» ведёт свой бизнес с различными клиентами, но имеет особые дружеские отношения с русским преступным синдикатом «Hotel Moscow». Команда берёт на себя разнообразные миссии — от перестрелок и рукопашных боёв, до морских баталий на водных просторах Юго-Восточной Азии.', false, NULL, 'Чёрная лагуна', 2002, 'chernaya-laguna', 'ONGOING', 'MANGA', NULL, NULL, 0);
INSERT INTO public.titles (id, content_rating, country_iso_code, created_at, created_by, description, is_licensed, main_cover_media_id, name, release_year, slug, title_status, type, updated_at, updated_by, version) VALUES ('019d6655-2654-7a18-9eb5-cee1ab1a1615', 'EIGHTEEN_PLUS', 'JP', '2026-04-07 05:05:49.65273+00', 'c834ce67-77c6-4eb1-9e63-6725af71aaba', 'Гатс, известный как Чёрный Мечник, ищет убежища от демонов, охотящихся за ним, и отмщения человеку, сделавшему из него жертву на своём алтаре. С помощью только своей нечеловеческой силы, умения и меча, Гатс должен биться против жестокого рока, пока битва с ненавистью мало-помалу лишает его человечности. Берсерк — это тёмная и погружающая в раздумья история о неистовых сражениях и безжалостном роке.', false, NULL, 'Берсерк', 1989, 'berserk', 'ONGOING', 'MANGA', NULL, NULL, 0);

ALTER TABLE public.titles ENABLE TRIGGER ALL;

--
-- title_authors
--

ALTER TABLE public.title_authors DISABLE TRIGGER ALL;

INSERT INTO public.title_authors (id, role, sort_order, version, author_id, title_id) VALUES ('019d627b-6909-7039-a60a-875a760d0773', 'STORY_AND_ART', 0, 0, '019d624f-62c6-7472-9724-12e0d90ab1cf', '019d627b-6900-77ef-a752-f474952fc14b');
INSERT INTO public.title_authors (id, role, sort_order, version, author_id, title_id) VALUES ('019d627c-fb71-7f4b-8567-d89ab328edf0', 'STORY_AND_ART', 0, 0, '019d624f-62c6-7472-9724-12e0d90ab1cf', '019d627c-fb71-7cd0-8825-3231a54d9dcb');
INSERT INTO public.title_authors (id, role, sort_order, version, author_id, title_id) VALUES ('019d627e-a622-7fc2-a991-6cfefa595bae', 'STORY_AND_ART', 0, 0, '019d624f-62c6-7472-9724-12e0d90ab1cf', '019d627e-a622-7c00-8927-7d86709b983e');
INSERT INTO public.title_authors (id, role, sort_order, version, author_id, title_id) VALUES ('019d6281-fda0-7c6a-925c-07f6200a6fef', 'STORY', 0, 0, '019d626c-a68c-7385-b494-b4bdb6361322', '019d6281-fda0-7649-8606-3eb3ef21f5d5');
INSERT INTO public.title_authors (id, role, sort_order, version, author_id, title_id) VALUES ('019d6281-fda0-7d6c-8569-985b741e1b13', 'ART', 1, 0, '019d626c-ef19-71fb-a4bb-e86547aa6a32', '019d6281-fda0-7649-8606-3eb3ef21f5d5');
INSERT INTO public.title_authors (id, role, sort_order, version, author_id, title_id) VALUES ('019d6286-8d66-7aa7-966f-e47ab087f435', 'STORY', 0, 0, '019d626c-a68c-7385-b494-b4bdb6361322', '019d6286-8d66-76b8-87aa-34307fe9c34b');
INSERT INTO public.title_authors (id, role, sort_order, version, author_id, title_id) VALUES ('019d6286-8d66-7b16-96cb-553bbe5afc22', 'ART', 1, 0, '019d626c-ef19-71fb-a4bb-e86547aa6a32', '019d6286-8d66-76b8-87aa-34307fe9c34b');
INSERT INTO public.title_authors (id, role, sort_order, version, author_id, title_id) VALUES ('019d6288-ea9b-7097-8423-ab3cc68ae96c', 'STORY', 0, 0, '019d626c-a68c-7385-b494-b4bdb6361322', '019d6288-ea99-7cf9-8047-cf1012f226bb');
INSERT INTO public.title_authors (id, role, sort_order, version, author_id, title_id) VALUES ('019d628e-6ba6-7ac0-a766-17d8a844527f', 'STORY', 0, 0, '019d626c-a68c-7385-b494-b4bdb6361322', '019d628e-6ba6-76b0-a45e-a925f7b5f100');
INSERT INTO public.title_authors (id, role, sort_order, version, author_id, title_id) VALUES ('019d628e-6ba6-7b37-93fb-baea6412f1be', 'ART', 1, 0, '019d6274-8fec-770e-a85e-f06e6f6b4aae', '019d628e-6ba6-76b0-a45e-a925f7b5f100');
INSERT INTO public.title_authors (id, role, sort_order, version, author_id, title_id) VALUES ('019d6290-58cc-7256-a206-ffa6b736f6ba', 'STORY_AND_ART', 0, 0, '019d626f-524e-749f-8468-d1ce33566ab7', '019d6290-58cb-7efd-a92e-f588d8584763');
INSERT INTO public.title_authors (id, role, sort_order, version, author_id, title_id) VALUES ('019d6292-643c-7537-80ea-b2979b9c996c', 'STORY', 0, 0, '019d626f-927d-7a08-8bab-1d9a042b77ba', '019d6292-643b-7158-bdfa-0675ac233327');
INSERT INTO public.title_authors (id, role, sort_order, version, author_id, title_id) VALUES ('019d6292-643c-75f7-a16a-0544ecbffb98', 'ART', 1, 0, '019d626f-b17b-7564-9dfb-bbed2852d532', '019d6292-643b-7158-bdfa-0675ac233327');
INSERT INTO public.title_authors (id, role, sort_order, version, author_id, title_id) VALUES ('019d6295-9378-77d7-b2b7-48d01ab05170', 'STORY', 0, 0, '019d6270-00b6-7da5-9291-549fd5badcec', '019d6295-9378-72b0-b05e-a1d7ce67b029');
INSERT INTO public.title_authors (id, role, sort_order, version, author_id, title_id) VALUES ('019d6295-9378-7872-b9b7-808c307ecda3', 'ART', 1, 0, '019d6270-4259-7697-b53e-9bc8e7ae3a66', '019d6295-9378-72b0-b05e-a1d7ce67b029');
INSERT INTO public.title_authors (id, role, sort_order, version, author_id, title_id) VALUES ('019d6297-69f9-7451-8616-ba12dd672f12', 'STORY_AND_ART', 0, 0, '019d6270-f820-758d-94a7-0574d0c58b61', '019d6297-69f8-7de3-b468-6b1057989e9f');
INSERT INTO public.title_authors (id, role, sort_order, version, author_id, title_id) VALUES ('019d6299-7e9f-73ba-bd6e-75eb5321b562', 'STORY_AND_ART', 0, 0, '019d6271-1565-7a5e-a4d2-2faabc49ee0c', '019d6299-7e9f-7116-98d8-962e29e38ec3');
INSERT INTO public.title_authors (id, role, sort_order, version, author_id, title_id) VALUES ('019d629d-1409-728b-a42d-ef37cb4c36d9', 'STORY_AND_ART', 0, 0, '019d6272-4296-79a5-a652-71199717b80e', '019d629d-1408-7e31-9fa6-72a58ef01d4d');
INSERT INTO public.title_authors (id, role, sort_order, version, author_id, title_id) VALUES ('019d629e-3a4c-703d-95b2-27536c939eab', 'STORY_AND_ART', 0, 0, '019d6273-b58e-72d4-adfa-135f98d3d660', '019d629e-3a4b-7d74-bedd-8520badc644b');
INSERT INTO public.title_authors (id, role, sort_order, version, author_id, title_id) VALUES ('019d6642-18a6-7960-9493-e0c0a8d739c7', 'STORY', 0, 0, '019d6271-4af5-7358-b7f7-ed673ca67066', '019d6642-18a2-7db6-b7d5-8f7f9f5f375e');
INSERT INTO public.title_authors (id, role, sort_order, version, author_id, title_id) VALUES ('019d6642-18a6-7a2d-a621-30f31ae97b59', 'ART', 1, 0, '019d6271-6e54-7a6e-981b-16eff75ebfae', '019d6642-18a2-7db6-b7d5-8f7f9f5f375e');
INSERT INTO public.title_authors (id, role, sort_order, version, author_id, title_id) VALUES ('019d6646-1701-7e00-98d4-57e053bf4e25', 'STORY_AND_ART', 0, 0, '019d6271-ab38-72cc-98eb-2d4c80aed1fa', '019d6646-1701-71e7-91cf-4936b494d9f7');
INSERT INTO public.title_authors (id, role, sort_order, version, author_id, title_id) VALUES ('019d664c-3049-7558-b3bd-9efae80f3d68', 'STORY_AND_ART', 0, 0, '019d6272-06f1-7d6c-ba26-d052dd279bfd', '019d664c-3048-7ec8-9092-33a5bfa2e515');
INSERT INTO public.title_authors (id, role, sort_order, version, author_id, title_id) VALUES ('019d664e-3f47-7360-b824-932d8c5ad39b', 'STORY', 0, 0, '019d6272-7e9b-717c-aaa8-4d9c7a6baa51', '019d664e-3f46-7e7e-a97d-7ae0f15601ab');
INSERT INTO public.title_authors (id, role, sort_order, version, author_id, title_id) VALUES ('019d6650-4e6a-7404-8931-f526ea1633bf', 'STORY_AND_ART', 0, 0, '019d6272-c0f4-7ced-a544-18ff845e1113', '019d6650-4e69-7fc2-bdf5-2a18fca27084');
INSERT INTO public.title_authors (id, role, sort_order, version, author_id, title_id) VALUES ('019d6652-fbce-7204-ba26-bb466899f748', 'STORY_AND_ART', 0, 0, '019d6272-fc42-7b33-bdd5-6361888c3bd5', '019d6652-fbcd-7e8b-9609-d27f7a8822de');
INSERT INTO public.title_authors (id, role, sort_order, version, author_id, title_id) VALUES ('019d6655-2654-7df7-b00f-bed35d23e4ed', 'STORY_AND_ART', 0, 0, '019d6273-4c65-7841-a7b3-a473a30a00b0', '019d6655-2654-7a18-9eb5-cee1ab1a1615');
INSERT INTO public.title_authors (id, role, sort_order, version, author_id, title_id) VALUES ('019d6655-2654-7eb0-a45c-fafe1d3787d2', 'STORY_AND_ART', 1, 0, '019d6273-8452-7814-bcf2-eccbc3d078d7', '019d6655-2654-7a18-9eb5-cee1ab1a1615');


ALTER TABLE public.title_authors ENABLE TRIGGER ALL;

--
-- title_tags
--

ALTER TABLE public.title_tags DISABLE TRIGGER ALL;

INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d627b-6900-77ef-a752-f474952fc14b', '019d6255-f62a-71fb-90f5-c26b2ed28191');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d627b-6900-77ef-a752-f474952fc14b', '019d6256-2a69-7fae-a703-b4634d7466bc');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d627b-6900-77ef-a752-f474952fc14b', '019d6256-58a4-75ce-816a-e28779565488');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d627b-6900-77ef-a752-f474952fc14b', '019d6256-ff93-7df3-810b-fcb1cfa4ebcd');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d627b-6900-77ef-a752-f474952fc14b', '019d6256-3d3a-79db-a9a0-247341d7e891');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d627b-6900-77ef-a752-f474952fc14b', '019d6255-e227-7ba5-adb5-b73636ed182f');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d627b-6900-77ef-a752-f474952fc14b', '019d6256-b376-7feb-83d4-820fc1b09fdd');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d627b-6900-77ef-a752-f474952fc14b', '019d6257-1c36-76a3-9fe8-200d521b6caf');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d627b-6900-77ef-a752-f474952fc14b', '019d6256-7772-7933-b0f2-83963d5e11a4');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d627b-6900-77ef-a752-f474952fc14b', '019d6257-569c-7c8f-8630-5ca77f1e97ae');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d627b-6900-77ef-a752-f474952fc14b', '019d6255-97ed-73ae-a5af-b2208eba3a41');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d627b-6900-77ef-a752-f474952fc14b', '019d6256-eabf-79e3-b6c6-ecc763119510');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d627b-6900-77ef-a752-f474952fc14b', '019d6256-15c4-766e-987f-771bf8d34ca7');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d627b-6900-77ef-a752-f474952fc14b', '019d6257-3912-72e9-b7f2-212fbc7e8a61');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d627b-6900-77ef-a752-f474952fc14b', '019d6256-d4ac-7a35-a3bb-7608439969ff');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d627b-6900-77ef-a752-f474952fc14b', '019d624f-ae95-7347-8c31-d32ba9fc33b4');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d627c-fb71-7cd0-8825-3231a54d9dcb', '019d6255-97ed-73ae-a5af-b2208eba3a41');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d627c-fb71-7cd0-8825-3231a54d9dcb', '019d6256-7772-7933-b0f2-83963d5e11a4');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d627c-fb71-7cd0-8825-3231a54d9dcb', '019d6257-8dd2-7c18-b296-30616d0f9a3a');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d627c-fb71-7cd0-8825-3231a54d9dcb', '019d6256-93c1-715c-b453-0337d1883129');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d627c-fb71-7cd0-8825-3231a54d9dcb', '019d6255-f62a-71fb-90f5-c26b2ed28191');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d627c-fb71-7cd0-8825-3231a54d9dcb', '019d6258-53d0-71d7-8467-50d48f9727d7');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d627e-a622-7c00-8927-7d86709b983e', '019d6256-93c1-715c-b453-0337d1883129');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d627e-a622-7c00-8927-7d86709b983e', '019d6257-d329-726a-83db-e494952737ec');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d627e-a622-7c00-8927-7d86709b983e', '019d6258-53d0-71d7-8467-50d48f9727d7');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d627e-a622-7c00-8927-7d86709b983e', '019d6256-15c4-766e-987f-771bf8d34ca7');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d627e-a622-7c00-8927-7d86709b983e', '019d6257-b738-7672-be90-7b6f6c967830');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d627e-a622-7c00-8927-7d86709b983e', '019d6256-b376-7feb-83d4-820fc1b09fdd');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6281-fda0-7649-8606-3eb3ef21f5d5', '019d6258-e724-7a83-b806-d288707bbfa8');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6281-fda0-7649-8606-3eb3ef21f5d5', '019d625b-2ccc-7649-b866-bc3f33481443');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6281-fda0-7649-8606-3eb3ef21f5d5', '019d6256-b376-7feb-83d4-820fc1b09fdd');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6281-fda0-7649-8606-3eb3ef21f5d5', '019d6259-4f0a-77a9-be57-1b510b341392');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6281-fda0-7649-8606-3eb3ef21f5d5', '019d6256-d4ac-7a35-a3bb-7608439969ff');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6281-fda0-7649-8606-3eb3ef21f5d5', '019d625a-4ba3-7c1c-8d6d-0a0a7473ac53');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6281-fda0-7649-8606-3eb3ef21f5d5', '019d6257-3912-72e9-b7f2-212fbc7e8a61');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6281-fda0-7649-8606-3eb3ef21f5d5', '019d625a-6925-7178-ac84-4ddbdbe12b12');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6281-fda0-7649-8606-3eb3ef21f5d5', '019d625a-dad8-73ca-910c-5a5da10b48aa');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6281-fda0-7649-8606-3eb3ef21f5d5', '019d6256-7772-7933-b0f2-83963d5e11a4');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6281-fda0-7649-8606-3eb3ef21f5d5', '019d6257-b738-7672-be90-7b6f6c967830');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6281-fda0-7649-8606-3eb3ef21f5d5', '019d624f-ae95-7347-8c31-d32ba9fc33b4');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6281-fda0-7649-8606-3eb3ef21f5d5', '019d6264-76a5-7753-aa3f-8c90840691de');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6281-fda0-7649-8606-3eb3ef21f5d5', '019d625a-2f66-7c6a-ab5a-1f7758e412c7');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6281-fda0-7649-8606-3eb3ef21f5d5', '019d6258-53d0-71d7-8467-50d48f9727d7');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6281-fda0-7649-8606-3eb3ef21f5d5', '019d6259-05ff-7edd-b4cc-2c7bf7aebc4b');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6281-fda0-7649-8606-3eb3ef21f5d5', '019d6258-a919-78f5-a85f-e4e6996c200d');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6281-fda0-7649-8606-3eb3ef21f5d5', '019d625a-1529-7ee1-8307-c748560de23d');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6281-fda0-7649-8606-3eb3ef21f5d5', '019d6256-2a69-7fae-a703-b4634d7466bc');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6281-fda0-7649-8606-3eb3ef21f5d5', '019d6255-f62a-71fb-90f5-c26b2ed28191');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6281-fda0-7649-8606-3eb3ef21f5d5', '019d6259-b79c-71c2-8b52-6d97bdceb916');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6281-fda0-7649-8606-3eb3ef21f5d5', '019d6257-1c36-76a3-9fe8-200d521b6caf');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6281-fda0-7649-8606-3eb3ef21f5d5', '019d6259-f67f-794f-8c5d-26aaee54fa9b');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6281-fda0-7649-8606-3eb3ef21f5d5', '019d6258-239e-7e2d-9f8c-66de81ea5b8f');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6281-fda0-7649-8606-3eb3ef21f5d5', '019d6258-c7c3-7683-9711-9e3358899f74');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6281-fda0-7649-8606-3eb3ef21f5d5', '019d6259-21fe-7018-876b-7a5af3d23494');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6281-fda0-7649-8606-3eb3ef21f5d5', '019d6255-e227-7ba5-adb5-b73636ed182f');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6281-fda0-7649-8606-3eb3ef21f5d5', '019d6256-eabf-79e3-b6c6-ecc763119510');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6281-fda0-7649-8606-3eb3ef21f5d5', '019d6256-ff93-7df3-810b-fcb1cfa4ebcd');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6281-fda0-7649-8606-3eb3ef21f5d5', '019d6256-15c4-766e-987f-771bf8d34ca7');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6281-fda0-7649-8606-3eb3ef21f5d5', '019d6259-7263-7af5-8b93-a95c49beec76');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6286-8d66-76b8-87aa-34307fe9c34b', '019d6259-f67f-794f-8c5d-26aaee54fa9b');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6286-8d66-76b8-87aa-34307fe9c34b', '019d6258-c7c3-7683-9711-9e3358899f74');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6286-8d66-76b8-87aa-34307fe9c34b', '019d625b-b3a4-7174-9d76-04eb6c8191e4');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6286-8d66-76b8-87aa-34307fe9c34b', '019d6258-53d0-71d7-8467-50d48f9727d7');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6286-8d66-76b8-87aa-34307fe9c34b', '019d6256-2a69-7fae-a703-b4634d7466bc');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6286-8d66-76b8-87aa-34307fe9c34b', '019d625b-2ccc-7649-b866-bc3f33481443');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6286-8d66-76b8-87aa-34307fe9c34b', '019d6256-15c4-766e-987f-771bf8d34ca7');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6286-8d66-76b8-87aa-34307fe9c34b', '019d625b-8ce6-7af9-8482-fa80817b6e5c');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6286-8d66-76b8-87aa-34307fe9c34b', '019d6256-58a4-75ce-816a-e28779565488');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6286-8d66-76b8-87aa-34307fe9c34b', '019d6257-1c36-76a3-9fe8-200d521b6caf');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6286-8d66-76b8-87aa-34307fe9c34b', '019d625b-53b1-7c18-8404-27c519950f23');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6286-8d66-76b8-87aa-34307fe9c34b', '019d6257-d329-726a-83db-e494952737ec');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6286-8d66-76b8-87aa-34307fe9c34b', '019d6257-b738-7672-be90-7b6f6c967830');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6286-8d66-76b8-87aa-34307fe9c34b', '019d6256-7772-7933-b0f2-83963d5e11a4');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6288-ea99-7cf9-8047-cf1012f226bb', '019d625b-8ce6-7af9-8482-fa80817b6e5c');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6288-ea99-7cf9-8047-cf1012f226bb', '019d6256-7772-7933-b0f2-83963d5e11a4');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6288-ea99-7cf9-8047-cf1012f226bb', '019d6257-b738-7672-be90-7b6f6c967830');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6288-ea99-7cf9-8047-cf1012f226bb', '019d625b-f8b2-767a-bdf4-d3ea416d5d84');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6288-ea99-7cf9-8047-cf1012f226bb', '019d6258-c7c3-7683-9711-9e3358899f74');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6288-ea99-7cf9-8047-cf1012f226bb', '019d6259-f67f-794f-8c5d-26aaee54fa9b');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6288-ea99-7cf9-8047-cf1012f226bb', '019d625b-d7d7-7d02-b117-9eebd6e9e938');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6288-ea99-7cf9-8047-cf1012f226bb', '019d6257-1c36-76a3-9fe8-200d521b6caf');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6288-ea99-7cf9-8047-cf1012f226bb', '019d6256-2a69-7fae-a703-b4634d7466bc');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6288-ea99-7cf9-8047-cf1012f226bb', '019d6256-58a4-75ce-816a-e28779565488');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6288-ea99-7cf9-8047-cf1012f226bb', '019d6256-15c4-766e-987f-771bf8d34ca7');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d628e-6ba6-76b0-a45e-a925f7b5f100', '019d6257-b738-7672-be90-7b6f6c967830');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d628e-6ba6-76b0-a45e-a925f7b5f100', '019d6257-3912-72e9-b7f2-212fbc7e8a61');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d628e-6ba6-76b0-a45e-a925f7b5f100', '019d625b-d7d7-7d02-b117-9eebd6e9e938');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d628e-6ba6-76b0-a45e-a925f7b5f100', '019d6256-2a69-7fae-a703-b4634d7466bc');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d628e-6ba6-76b0-a45e-a925f7b5f100', '019d6257-1c36-76a3-9fe8-200d521b6caf');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d628e-6ba6-76b0-a45e-a925f7b5f100', '019d6259-21fe-7018-876b-7a5af3d23494');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d628e-6ba6-76b0-a45e-a925f7b5f100', '019d6256-7772-7933-b0f2-83963d5e11a4');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d628e-6ba6-76b0-a45e-a925f7b5f100', '019d625c-30f3-7fe3-be1a-f6529072e9e6');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d628e-6ba6-76b0-a45e-a925f7b5f100', '019d6259-05ff-7edd-b4cc-2c7bf7aebc4b');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d628e-6ba6-76b0-a45e-a925f7b5f100', '019d6256-ff93-7df3-810b-fcb1cfa4ebcd');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d628e-6ba6-76b0-a45e-a925f7b5f100', '019d6257-d329-726a-83db-e494952737ec');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d628e-6ba6-76b0-a45e-a925f7b5f100', '019d6256-15c4-766e-987f-771bf8d34ca7');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d628e-6ba6-76b0-a45e-a925f7b5f100', '019d6257-8dd2-7c18-b296-30616d0f9a3a');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d628e-6ba6-76b0-a45e-a925f7b5f100', '019d625b-2ccc-7649-b866-bc3f33481443');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d628e-6ba6-76b0-a45e-a925f7b5f100', '019d6256-58a4-75ce-816a-e28779565488');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d628e-6ba6-76b0-a45e-a925f7b5f100', '019d6258-c7c3-7683-9711-9e3358899f74');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6290-58cb-7efd-a92e-f588d8584763', '019d625d-3a96-71d7-b963-e9782b7c7721');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6290-58cb-7efd-a92e-f588d8584763', '019d6255-97ed-73ae-a5af-b2208eba3a41');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6290-58cb-7efd-a92e-f588d8584763', '019d6256-7772-7933-b0f2-83963d5e11a4');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6290-58cb-7efd-a92e-f588d8584763', '019d625d-9221-7c72-8373-464f7bd7b713');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6290-58cb-7efd-a92e-f588d8584763', '019d6256-93c1-715c-b453-0337d1883129');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6290-58cb-7efd-a92e-f588d8584763', '019d625d-60de-75db-aeb3-a2aa270b6fd1');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6290-58cb-7efd-a92e-f588d8584763', '019d625c-a8e0-7d37-b3d2-8241d455c5e0');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6290-58cb-7efd-a92e-f588d8584763', '019d625c-f61b-7c31-ad50-4d59af12220b');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6290-58cb-7efd-a92e-f588d8584763', '019d6256-3d3a-79db-a9a0-247341d7e891');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6290-58cb-7efd-a92e-f588d8584763', '019d625c-97d2-7620-b640-e10e889bc2bf');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6292-643b-7158-bdfa-0675ac233327', '019d625e-8763-7245-9e05-f372a83c309c');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6292-643b-7158-bdfa-0675ac233327', '019d6255-e227-7ba5-adb5-b73636ed182f');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6292-643b-7158-bdfa-0675ac233327', '019d6255-f62a-71fb-90f5-c26b2ed28191');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6292-643b-7158-bdfa-0675ac233327', '019d625e-9c68-7fc6-9c2f-95cae9c2b302');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6292-643b-7158-bdfa-0675ac233327', '019d625d-ba04-707a-a543-9d52166318ce');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6292-643b-7158-bdfa-0675ac233327', '019d625e-d62e-7f58-9ecb-5d0c8d5905cc');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6292-643b-7158-bdfa-0675ac233327', '019d6256-7772-7933-b0f2-83963d5e11a4');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6292-643b-7158-bdfa-0675ac233327', '019d6256-ff93-7df3-810b-fcb1cfa4ebcd');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6292-643b-7158-bdfa-0675ac233327', '019d625a-1529-7ee1-8307-c748560de23d');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6292-643b-7158-bdfa-0675ac233327', '019d625e-eded-722d-96d7-eed83f04fbf0');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6292-643b-7158-bdfa-0675ac233327', '019d625f-0b6c-7039-9e31-061564cd6182');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6292-643b-7158-bdfa-0675ac233327', '019d6259-b79c-71c2-8b52-6d97bdceb916');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6292-643b-7158-bdfa-0675ac233327', '019d6256-3d3a-79db-a9a0-247341d7e891');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6292-643b-7158-bdfa-0675ac233327', '019d6259-21fe-7018-876b-7a5af3d23494');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6292-643b-7158-bdfa-0675ac233327', '019d625c-f61b-7c31-ad50-4d59af12220b');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6292-643b-7158-bdfa-0675ac233327', '019d625e-239c-725e-9f05-0dd9c9677400');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6292-643b-7158-bdfa-0675ac233327', '019d6255-97ed-73ae-a5af-b2208eba3a41');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6292-643b-7158-bdfa-0675ac233327', '019d6257-8dd2-7c18-b296-30616d0f9a3a');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6292-643b-7158-bdfa-0675ac233327', '019d625e-b43c-7fc6-97ba-b67e4a2ed64f');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6292-643b-7158-bdfa-0675ac233327', '019d625d-60de-75db-aeb3-a2aa270b6fd1');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6292-643b-7158-bdfa-0675ac233327', '019d625e-4f62-7937-b4ca-61139a05347f');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6295-9378-72b0-b05e-a1d7ce67b029', '019d6257-1c36-76a3-9fe8-200d521b6caf');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6295-9378-72b0-b05e-a1d7ce67b029', '019d6257-b738-7672-be90-7b6f6c967830');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6295-9378-72b0-b05e-a1d7ce67b029', '019d625c-30f3-7fe3-be1a-f6529072e9e6');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6295-9378-72b0-b05e-a1d7ce67b029', '019d6256-7772-7933-b0f2-83963d5e11a4');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6295-9378-72b0-b05e-a1d7ce67b029', '019d6255-f62a-71fb-90f5-c26b2ed28191');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6295-9378-72b0-b05e-a1d7ce67b029', '019d6256-58a4-75ce-816a-e28779565488');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6295-9378-72b0-b05e-a1d7ce67b029', '019d625a-4ba3-7c1c-8d6d-0a0a7473ac53');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6295-9378-72b0-b05e-a1d7ce67b029', '019d6258-c7c3-7683-9711-9e3358899f74');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6295-9378-72b0-b05e-a1d7ce67b029', '019d625f-3488-7353-9db8-f3635201783f');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6295-9378-72b0-b05e-a1d7ce67b029', '019d6256-d4ac-7a35-a3bb-7608439969ff');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6295-9378-72b0-b05e-a1d7ce67b029', '019d6258-53d0-71d7-8467-50d48f9727d7');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6295-9378-72b0-b05e-a1d7ce67b029', '019d625f-8c8b-7249-9ccc-3993879a5168');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6295-9378-72b0-b05e-a1d7ce67b029', '019d6257-569c-7c8f-8630-5ca77f1e97ae');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6295-9378-72b0-b05e-a1d7ce67b029', '019d6259-f67f-794f-8c5d-26aaee54fa9b');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6295-9378-72b0-b05e-a1d7ce67b029', '019d6257-3912-72e9-b7f2-212fbc7e8a61');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6297-69f8-7de3-b468-6b1057989e9f', '019d6255-97ed-73ae-a5af-b2208eba3a41');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6297-69f8-7de3-b468-6b1057989e9f', '019d6264-76a5-7753-aa3f-8c90840691de');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6297-69f8-7de3-b468-6b1057989e9f', '019d6257-1c36-76a3-9fe8-200d521b6caf');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6297-69f8-7de3-b468-6b1057989e9f', '019d624f-ae95-7347-8c31-d32ba9fc33b4');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6297-69f8-7de3-b468-6b1057989e9f', '019d625d-ba04-707a-a543-9d52166318ce');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6297-69f8-7de3-b468-6b1057989e9f', '019d625f-fdbd-7010-8900-0c9d35b54040');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6297-69f8-7de3-b468-6b1057989e9f', '019d6257-d329-726a-83db-e494952737ec');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6297-69f8-7de3-b468-6b1057989e9f', '019d6256-b376-7feb-83d4-820fc1b09fdd');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6297-69f8-7de3-b468-6b1057989e9f', '019d625a-4ba3-7c1c-8d6d-0a0a7473ac53');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6297-69f8-7de3-b468-6b1057989e9f', '019d6260-3067-7e04-85a3-c00dbf6cf363');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6297-69f8-7de3-b468-6b1057989e9f', '019d6255-f62a-71fb-90f5-c26b2ed28191');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6297-69f8-7de3-b468-6b1057989e9f', '019d625b-53b1-7c18-8404-27c519950f23');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6297-69f8-7de3-b468-6b1057989e9f', '019d6257-8dd2-7c18-b296-30616d0f9a3a');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6297-69f8-7de3-b468-6b1057989e9f', '019d625b-b3a4-7174-9d76-04eb6c8191e4');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6297-69f8-7de3-b468-6b1057989e9f', '019d6256-58a4-75ce-816a-e28779565488');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6297-69f8-7de3-b468-6b1057989e9f', '019d6258-53d0-71d7-8467-50d48f9727d7');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6299-7e9f-7116-98d8-962e29e38ec3', '019d6255-97ed-73ae-a5af-b2208eba3a41');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6299-7e9f-7116-98d8-962e29e38ec3', '019d6257-1c36-76a3-9fe8-200d521b6caf');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6299-7e9f-7116-98d8-962e29e38ec3', '019d6262-a22a-7ccc-9c56-90d29c43f516');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6299-7e9f-7116-98d8-962e29e38ec3', '019d6256-b376-7feb-83d4-820fc1b09fdd');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6299-7e9f-7116-98d8-962e29e38ec3', '019d6259-f67f-794f-8c5d-26aaee54fa9b');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6299-7e9f-7116-98d8-962e29e38ec3', '019d6255-f62a-71fb-90f5-c26b2ed28191');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6299-7e9f-7116-98d8-962e29e38ec3', '019d625f-3488-7353-9db8-f3635201783f');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6299-7e9f-7116-98d8-962e29e38ec3', '019d6256-2a69-7fae-a703-b4634d7466bc');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6299-7e9f-7116-98d8-962e29e38ec3', '019d6255-e227-7ba5-adb5-b73636ed182f');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6299-7e9f-7116-98d8-962e29e38ec3', '019d6256-d4ac-7a35-a3bb-7608439969ff');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6299-7e9f-7116-98d8-962e29e38ec3', '019d625a-4ba3-7c1c-8d6d-0a0a7473ac53');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6299-7e9f-7116-98d8-962e29e38ec3', '019d625b-d7d7-7d02-b117-9eebd6e9e938');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6299-7e9f-7116-98d8-962e29e38ec3', '019d6256-15c4-766e-987f-771bf8d34ca7');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6299-7e9f-7116-98d8-962e29e38ec3', '019d6260-dd3b-78d4-8c33-9ad2934eabf7');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6299-7e9f-7116-98d8-962e29e38ec3', '019d6258-53d0-71d7-8467-50d48f9727d7');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6299-7e9f-7116-98d8-962e29e38ec3', '019d6259-b79c-71c2-8b52-6d97bdceb916');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6299-7e9f-7116-98d8-962e29e38ec3', '019d6261-50eb-772f-b8ce-c0429b3700db');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6299-7e9f-7116-98d8-962e29e38ec3', '019d6261-3456-749f-921c-2d80c7fbd307');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6299-7e9f-7116-98d8-962e29e38ec3', '019d6256-7772-7933-b0f2-83963d5e11a4');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6299-7e9f-7116-98d8-962e29e38ec3', '019d625c-30f3-7fe3-be1a-f6529072e9e6');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6299-7e9f-7116-98d8-962e29e38ec3', '019d625d-ba04-707a-a543-9d52166318ce');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6299-7e9f-7116-98d8-962e29e38ec3', '019d6257-b738-7672-be90-7b6f6c967830');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6299-7e9f-7116-98d8-962e29e38ec3', '019d625b-53b1-7c18-8404-27c519950f23');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6299-7e9f-7116-98d8-962e29e38ec3', '019d625c-f61b-7c31-ad50-4d59af12220b');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d629d-1408-7e31-9fa6-72a58ef01d4d', '019d6255-97ed-73ae-a5af-b2208eba3a41');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d629d-1408-7e31-9fa6-72a58ef01d4d', '019d6264-afc5-7ebc-ab26-a32347ac81ae');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d629d-1408-7e31-9fa6-72a58ef01d4d', '019d6257-8dd2-7c18-b296-30616d0f9a3a');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d629e-3a4b-7d74-bedd-8520badc644b', '019d625b-53b1-7c18-8404-27c519950f23');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d629e-3a4b-7d74-bedd-8520badc644b', '019d6255-f62a-71fb-90f5-c26b2ed28191');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d629e-3a4b-7d74-bedd-8520badc644b', '019d6258-53d0-71d7-8467-50d48f9727d7');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d629e-3a4b-7d74-bedd-8520badc644b', '019d6257-b738-7672-be90-7b6f6c967830');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6642-18a2-7db6-b7d5-8f7f9f5f375e', '019d6258-53d0-71d7-8467-50d48f9727d7');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6642-18a2-7db6-b7d5-8f7f9f5f375e', '019d624f-ae95-7347-8c31-d32ba9fc33b4');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6642-18a2-7db6-b7d5-8f7f9f5f375e', '019d6256-93c1-715c-b453-0337d1883129');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6642-18a2-7db6-b7d5-8f7f9f5f375e', '019d6263-1d17-7fca-8789-b824cfbe5725');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6642-18a2-7db6-b7d5-8f7f9f5f375e', '019d6259-05ff-7edd-b4cc-2c7bf7aebc4b');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6642-18a2-7db6-b7d5-8f7f9f5f375e', '019d6256-7772-7933-b0f2-83963d5e11a4');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6642-18a2-7db6-b7d5-8f7f9f5f375e', '019d6256-b376-7feb-83d4-820fc1b09fdd');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6642-18a2-7db6-b7d5-8f7f9f5f375e', '019d6262-f07c-745e-8a3a-aaad012e4ceb');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6642-18a2-7db6-b7d5-8f7f9f5f375e', '019d6255-e227-7ba5-adb5-b73636ed182f');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6642-18a2-7db6-b7d5-8f7f9f5f375e', '019d625b-8ce6-7af9-8482-fa80817b6e5c');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6642-18a2-7db6-b7d5-8f7f9f5f375e', '019d6259-21fe-7018-876b-7a5af3d23494');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6642-18a2-7db6-b7d5-8f7f9f5f375e', '019d6258-a919-78f5-a85f-e4e6996c200d');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6642-18a2-7db6-b7d5-8f7f9f5f375e', '019d6263-0423-7056-b5b5-8f1ecbc26bbd');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6642-18a2-7db6-b7d5-8f7f9f5f375e', '019d6257-1c36-76a3-9fe8-200d521b6caf');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6642-18a2-7db6-b7d5-8f7f9f5f375e', '019d6256-d4ac-7a35-a3bb-7608439969ff');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6642-18a2-7db6-b7d5-8f7f9f5f375e', '019d6256-2a69-7fae-a703-b4634d7466bc');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6642-18a2-7db6-b7d5-8f7f9f5f375e', '019d6256-eabf-79e3-b6c6-ecc763119510');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6646-1701-71e7-91cf-4936b494d9f7', '019d6256-7772-7933-b0f2-83963d5e11a4');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6646-1701-71e7-91cf-4936b494d9f7', '019d6255-f62a-71fb-90f5-c26b2ed28191');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6646-1701-71e7-91cf-4936b494d9f7', '019d6258-53d0-71d7-8467-50d48f9727d7');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6646-1701-71e7-91cf-4936b494d9f7', '019d6256-d4ac-7a35-a3bb-7608439969ff');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6646-1701-71e7-91cf-4936b494d9f7', '019d6263-bdf3-7764-b371-7ba3f304825d');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6646-1701-71e7-91cf-4936b494d9f7', '019d6258-239e-7e2d-9f8c-66de81ea5b8f');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6646-1701-71e7-91cf-4936b494d9f7', '019d6257-1c36-76a3-9fe8-200d521b6caf');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6646-1701-71e7-91cf-4936b494d9f7', '019d625a-6925-7178-ac84-4ddbdbe12b12');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6646-1701-71e7-91cf-4936b494d9f7', '019d6259-05ff-7edd-b4cc-2c7bf7aebc4b');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6646-1701-71e7-91cf-4936b494d9f7', '019d6259-b79c-71c2-8b52-6d97bdceb916');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6646-1701-71e7-91cf-4936b494d9f7', '019d6256-15c4-766e-987f-771bf8d34ca7');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6646-1701-71e7-91cf-4936b494d9f7', '019d6257-b738-7672-be90-7b6f6c967830');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6646-1701-71e7-91cf-4936b494d9f7', '019d6263-1d17-7fca-8789-b824cfbe5725');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6646-1701-71e7-91cf-4936b494d9f7', '019d6268-d260-700c-bf7a-d106ea219b06');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6646-1701-71e7-91cf-4936b494d9f7', '019d624f-ae95-7347-8c31-d32ba9fc33b4');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6646-1701-71e7-91cf-4936b494d9f7', '019d6263-998b-780c-81de-2737160d9e06');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6646-1701-71e7-91cf-4936b494d9f7', '019d625a-2f66-7c6a-ab5a-1f7758e412c7');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6646-1701-71e7-91cf-4936b494d9f7', '019d6263-4eac-707a-a176-da361d0c2509');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d664c-3048-7ec8-9092-33a5bfa2e515', '019d6264-46b0-720c-9c90-eacbf03f90e1');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d664c-3048-7ec8-9092-33a5bfa2e515', '019d6255-97ed-73ae-a5af-b2208eba3a41');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d664c-3048-7ec8-9092-33a5bfa2e515', '019d6259-f67f-794f-8c5d-26aaee54fa9b');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d664c-3048-7ec8-9092-33a5bfa2e515', '019d6257-569c-7c8f-8630-5ca77f1e97ae');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d664c-3048-7ec8-9092-33a5bfa2e515', '019d624f-ae95-7347-8c31-d32ba9fc33b4');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d664c-3048-7ec8-9092-33a5bfa2e515', '019d6257-d329-726a-83db-e494952737ec');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d664c-3048-7ec8-9092-33a5bfa2e515', '019d6256-7772-7933-b0f2-83963d5e11a4');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d664c-3048-7ec8-9092-33a5bfa2e515', '019d6255-f62a-71fb-90f5-c26b2ed28191');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d664c-3048-7ec8-9092-33a5bfa2e515', '019d625b-2ccc-7649-b866-bc3f33481443');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d664c-3048-7ec8-9092-33a5bfa2e515', '019d6256-b376-7feb-83d4-820fc1b09fdd');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d664c-3048-7ec8-9092-33a5bfa2e515', '019d6256-d4ac-7a35-a3bb-7608439969ff');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d664c-3048-7ec8-9092-33a5bfa2e515', '019d6255-e227-7ba5-adb5-b73636ed182f');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d664c-3048-7ec8-9092-33a5bfa2e515', '019d6258-53d0-71d7-8467-50d48f9727d7');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d664c-3048-7ec8-9092-33a5bfa2e515', '019d6257-b738-7672-be90-7b6f6c967830');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d664c-3048-7ec8-9092-33a5bfa2e515', '019d6259-4f0a-77a9-be57-1b510b341392');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d664c-3048-7ec8-9092-33a5bfa2e515', '019d6257-1c36-76a3-9fe8-200d521b6caf');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d664c-3048-7ec8-9092-33a5bfa2e515', '019d6264-23e3-75a9-8c1c-c5c856a18797');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d664c-3048-7ec8-9092-33a5bfa2e515', '019d6257-8dd2-7c18-b296-30616d0f9a3a');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d664c-3048-7ec8-9092-33a5bfa2e515', '019d625a-4ba3-7c1c-8d6d-0a0a7473ac53');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d664c-3048-7ec8-9092-33a5bfa2e515', '019d6264-76a5-7753-aa3f-8c90840691de');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d664e-3f46-7e7e-a97d-7ae0f15601ab', '019d625b-53b1-7c18-8404-27c519950f23');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d664e-3f46-7e7e-a97d-7ae0f15601ab', '019d6264-fa7a-7cf9-99b2-c6fe75def5a7');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d664e-3f46-7e7e-a97d-7ae0f15601ab', '019d6256-7772-7933-b0f2-83963d5e11a4');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d664e-3f46-7e7e-a97d-7ae0f15601ab', '019d6257-8dd2-7c18-b296-30616d0f9a3a');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d664e-3f46-7e7e-a97d-7ae0f15601ab', '019d6268-d260-700c-bf7a-d106ea219b06');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d664e-3f46-7e7e-a97d-7ae0f15601ab', '019d6255-97ed-73ae-a5af-b2208eba3a41');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d664e-3f46-7e7e-a97d-7ae0f15601ab', '019d6256-93c1-715c-b453-0337d1883129');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d664e-3f46-7e7e-a97d-7ae0f15601ab', '019d6255-f62a-71fb-90f5-c26b2ed28191');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d664e-3f46-7e7e-a97d-7ae0f15601ab', '019d6256-b376-7feb-83d4-820fc1b09fdd');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d664e-3f46-7e7e-a97d-7ae0f15601ab', '019d6257-d329-726a-83db-e494952737ec');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6650-4e69-7fc2-bdf5-2a18fca27084', '019d6255-97ed-73ae-a5af-b2208eba3a41');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6650-4e69-7fc2-bdf5-2a18fca27084', '019d6265-b877-7676-b2df-780e1285dd04');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6650-4e69-7fc2-bdf5-2a18fca27084', '019d6265-5c01-7845-84ca-348bfd3cafd6');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6650-4e69-7fc2-bdf5-2a18fca27084', '019d625b-2ccc-7649-b866-bc3f33481443');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6650-4e69-7fc2-bdf5-2a18fca27084', '019d625a-1529-7ee1-8307-c748560de23d');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6650-4e69-7fc2-bdf5-2a18fca27084', '019d6256-7772-7933-b0f2-83963d5e11a4');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6650-4e69-7fc2-bdf5-2a18fca27084', '019d624f-ae95-7347-8c31-d32ba9fc33b4');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6650-4e69-7fc2-bdf5-2a18fca27084', '019d6256-b376-7feb-83d4-820fc1b09fdd');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6650-4e69-7fc2-bdf5-2a18fca27084', '019d625b-d7d7-7d02-b117-9eebd6e9e938');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6650-4e69-7fc2-bdf5-2a18fca27084', '019d6259-b79c-71c2-8b52-6d97bdceb916');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6650-4e69-7fc2-bdf5-2a18fca27084', '019d6268-d260-700c-bf7a-d106ea219b06');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6650-4e69-7fc2-bdf5-2a18fca27084', '019d625d-ba04-707a-a543-9d52166318ce');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6650-4e69-7fc2-bdf5-2a18fca27084', '019d6265-d20a-7a24-b2b8-95867364dc64');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6650-4e69-7fc2-bdf5-2a18fca27084', '019d6255-f62a-71fb-90f5-c26b2ed28191');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6650-4e69-7fc2-bdf5-2a18fca27084', '019d6265-760e-7c8f-8fd5-3375fb6e908d');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6650-4e69-7fc2-bdf5-2a18fca27084', '019d625a-2f66-7c6a-ab5a-1f7758e412c7');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6650-4e69-7fc2-bdf5-2a18fca27084', '019d6266-0256-7d9d-8bb8-54bd50624e0f');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6650-4e69-7fc2-bdf5-2a18fca27084', '019d6265-2aff-7558-ae5e-0365d07d5957');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6650-4e69-7fc2-bdf5-2a18fca27084', '019d6255-e227-7ba5-adb5-b73636ed182f');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6650-4e69-7fc2-bdf5-2a18fca27084', '019d6258-53d0-71d7-8467-50d48f9727d7');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6652-fbcd-7e8b-9609-d27f7a8822de', '019d6257-b738-7672-be90-7b6f6c967830');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6652-fbcd-7e8b-9609-d27f7a8822de', '019d6259-21fe-7018-876b-7a5af3d23494');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6652-fbcd-7e8b-9609-d27f7a8822de', '019d6256-7772-7933-b0f2-83963d5e11a4');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6652-fbcd-7e8b-9609-d27f7a8822de', '019d6256-58a4-75ce-816a-e28779565488');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6652-fbcd-7e8b-9609-d27f7a8822de', '019d625b-2ccc-7649-b866-bc3f33481443');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6652-fbcd-7e8b-9609-d27f7a8822de', '019d625b-b3a4-7174-9d76-04eb6c8191e4');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6652-fbcd-7e8b-9609-d27f7a8822de', '019d6263-0423-7056-b5b5-8f1ecbc26bbd');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6652-fbcd-7e8b-9609-d27f7a8822de', '019d6266-64a9-7620-8266-191e371e92c6');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6652-fbcd-7e8b-9609-d27f7a8822de', '019d6267-2f40-79d2-9456-225c156345a5');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6652-fbcd-7e8b-9609-d27f7a8822de', '019d6256-d4ac-7a35-a3bb-7608439969ff');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6652-fbcd-7e8b-9609-d27f7a8822de', '019d6262-f07c-745e-8a3a-aaad012e4ceb');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6652-fbcd-7e8b-9609-d27f7a8822de', '019d6258-c7c3-7683-9711-9e3358899f74');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6652-fbcd-7e8b-9609-d27f7a8822de', '019d625b-8ce6-7af9-8482-fa80817b6e5c');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6652-fbcd-7e8b-9609-d27f7a8822de', '019d6257-3912-72e9-b7f2-212fbc7e8a61');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6652-fbcd-7e8b-9609-d27f7a8822de', '019d625a-6925-7178-ac84-4ddbdbe12b12');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6652-fbcd-7e8b-9609-d27f7a8822de', '019d6267-18b9-7c9b-b56c-79844c920738');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6652-fbcd-7e8b-9609-d27f7a8822de', '019d625a-4ba3-7c1c-8d6d-0a0a7473ac53');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6652-fbcd-7e8b-9609-d27f7a8822de', '019d6266-83b3-7800-a36b-0d49596fa693');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6652-fbcd-7e8b-9609-d27f7a8822de', '019d6259-f67f-794f-8c5d-26aaee54fa9b');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6652-fbcd-7e8b-9609-d27f7a8822de', '019d6257-1c36-76a3-9fe8-200d521b6caf');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6652-fbcd-7e8b-9609-d27f7a8822de', '019d625d-ba04-707a-a543-9d52166318ce');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6652-fbcd-7e8b-9609-d27f7a8822de', '019d624f-ae95-7347-8c31-d32ba9fc33b4');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6652-fbcd-7e8b-9609-d27f7a8822de', '019d6258-53d0-71d7-8467-50d48f9727d7');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6652-fbcd-7e8b-9609-d27f7a8822de', '019d6266-9f80-750e-a629-36f8d4d5ac6e');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6655-2654-7a18-9eb5-cee1ab1a1615', '019d625c-f61b-7c31-ad50-4d59af12220b');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6655-2654-7a18-9eb5-cee1ab1a1615', '019d6265-d20a-7a24-b2b8-95867364dc64');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6655-2654-7a18-9eb5-cee1ab1a1615', '019d6261-3456-749f-921c-2d80c7fbd307');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6655-2654-7a18-9eb5-cee1ab1a1615', '019d6256-7772-7933-b0f2-83963d5e11a4');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6655-2654-7a18-9eb5-cee1ab1a1615', '019d6258-53d0-71d7-8467-50d48f9727d7');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6655-2654-7a18-9eb5-cee1ab1a1615', '019d6267-85c9-7ad0-ab42-adf841f59acf');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6655-2654-7a18-9eb5-cee1ab1a1615', '019d625b-8ce6-7af9-8482-fa80817b6e5c');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6655-2654-7a18-9eb5-cee1ab1a1615', '019d6267-d74e-7651-98fb-fcce48470c2d');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6655-2654-7a18-9eb5-cee1ab1a1615', '019d625b-2ccc-7649-b866-bc3f33481443');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6655-2654-7a18-9eb5-cee1ab1a1615', '019d6256-58a4-75ce-816a-e28779565488');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6655-2654-7a18-9eb5-cee1ab1a1615', '019d6266-0256-7d9d-8bb8-54bd50624e0f');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6655-2654-7a18-9eb5-cee1ab1a1615', '019d6267-ae9d-7a62-90af-829c3e32781e');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6655-2654-7a18-9eb5-cee1ab1a1615', '019d6255-e227-7ba5-adb5-b73636ed182f');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6655-2654-7a18-9eb5-cee1ab1a1615', '019d6256-2a69-7fae-a703-b4634d7466bc');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6655-2654-7a18-9eb5-cee1ab1a1615', '019d6257-1c36-76a3-9fe8-200d521b6caf');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6655-2654-7a18-9eb5-cee1ab1a1615', '019d6257-b738-7672-be90-7b6f6c967830');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6655-2654-7a18-9eb5-cee1ab1a1615', '019d625c-97d2-7620-b640-e10e889bc2bf');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6655-2654-7a18-9eb5-cee1ab1a1615', '019d6256-d4ac-7a35-a3bb-7608439969ff');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6655-2654-7a18-9eb5-cee1ab1a1615', '019d6256-3d3a-79db-a9a0-247341d7e891');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6655-2654-7a18-9eb5-cee1ab1a1615', '019d624f-ae95-7347-8c31-d32ba9fc33b4');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6655-2654-7a18-9eb5-cee1ab1a1615', '019d625d-ba04-707a-a543-9d52166318ce');
INSERT INTO public.title_tags (title_id, tag_id) VALUES ('019d6655-2654-7a18-9eb5-cee1ab1a1615', '019d625d-60de-75db-aeb3-a2aa270b6fd1');


ALTER TABLE public.title_tags ENABLE TRIGGER ALL;
