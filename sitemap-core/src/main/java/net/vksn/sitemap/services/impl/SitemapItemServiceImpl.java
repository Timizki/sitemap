package net.vksn.sitemap.services.impl;
import java.util.List;
import java.util.Set;

import net.vksn.bedrock.exceptions.EntityNotFoundException;
import net.vksn.sitemap.dao.SitemapItemDAO;
import net.vksn.sitemap.model.Sitemap;
import net.vksn.sitemap.model.SitemapItem;
import net.vksn.sitemap.services.SitemapItemService;
import net.vksn.sitemap.services.SitemapService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SitemapItemServiceImpl implements SitemapItemService {

	@Autowired
	private SitemapItemDAO dao;
		
	@Autowired
	private SitemapService sitemapService;
	
	public SitemapItem getItem(int id) throws EntityNotFoundException {	
		return dao.get(id, null);
	}

	@Transactional(readOnly = true)
	public SitemapItem getItemByPath(int id, String[] path)
			throws EntityNotFoundException {
		Sitemap sitemap = sitemapService.getSitemap(id, true);
		SitemapItem item = null;
		try {
			item = dao.getItemByPath(sitemap, path);
		}
		catch(EntityNotFoundException e) {
			if( path.length == 0 || "index".equals(path[0])) {
				item = sitemap.getSitemapItems().iterator().next();
			}
			else {
				throw e;
			}
		}
		return item;
	}

	@Transactional(readOnly = true)
	public List<SitemapItem> getAllSitemapItems(int sitemapId) {
		return dao.getAllSitemapItems(sitemapId);
	}
	
	@Transactional
	public void storeSitemapItem(SitemapItem item) throws EntityNotFoundException {
		dao.store(item);
	}

	@Transactional
	public void deleteSitemapItem(int id) throws EntityNotFoundException {
		dao.delete(id);
	}

	@Transactional
	public void undeleteSitemapItem(int id) throws EntityNotFoundException {
		dao.undelete(id);
	}

	public void removeSitemapItem(int id) throws EntityNotFoundException {
		dao.remove(id);
	}

	@Transactional(readOnly = true)
	public Set<SitemapItem> getSiblings(SitemapItem item) {
		return dao.getSiblings(item);
	}

}
