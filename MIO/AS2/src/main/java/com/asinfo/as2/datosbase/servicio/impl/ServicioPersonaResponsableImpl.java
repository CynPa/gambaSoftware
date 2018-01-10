/*   1:    */ package com.asinfo.as2.datosbase.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.PersonaResponsableDao;
/*   4:    */ import com.asinfo.as2.datosbase.servicio.ServicioPersonaResponsable;
/*   5:    */ import com.asinfo.as2.entities.PersonaResponsable;
/*   6:    */ import java.util.ArrayList;
/*   7:    */ import java.util.HashMap;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.Map;
/*  10:    */ import javax.ejb.EJB;
/*  11:    */ import javax.ejb.Stateless;
/*  12:    */ 
/*  13:    */ @Stateless
/*  14:    */ public class ServicioPersonaResponsableImpl
/*  15:    */   implements ServicioPersonaResponsable
/*  16:    */ {
/*  17:    */   @EJB
/*  18:    */   private transient PersonaResponsableDao personaResponsableDao;
/*  19:    */   
/*  20:    */   public void guardar(PersonaResponsable personaResponsable)
/*  21:    */   {
/*  22: 45 */     this.personaResponsableDao.guardar(personaResponsable);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public int contarPorCriterio(Map<String, String> filters)
/*  26:    */   {
/*  27: 56 */     return this.personaResponsableDao.contarPorCriterio(filters);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public List<PersonaResponsable> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  31:    */   {
/*  32: 67 */     return this.personaResponsableDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public List<PersonaResponsable> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  36:    */   {
/*  37: 77 */     return this.personaResponsableDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  38:    */   }
/*  39:    */   
/*  40:    */   public void eliminar(PersonaResponsable personaResponsable)
/*  41:    */   {
/*  42: 82 */     this.personaResponsableDao.eliminar(personaResponsable);
/*  43:    */   }
/*  44:    */   
/*  45:    */   public List<PersonaResponsable> autocompletarMedico(String consulta, int idOrganizacion)
/*  46:    */   {
/*  47: 87 */     List<PersonaResponsable> lista = new ArrayList();
/*  48:    */     
/*  49: 89 */     String sortField = "apellidos";
/*  50: 90 */     HashMap<String, String> filters = new HashMap();
/*  51: 91 */     filters.put("apellidos", consulta.trim());
/*  52: 92 */     filters.put("nombres", consulta.trim());
/*  53: 93 */     filters.put("identificacion", consulta.trim());
/*  54: 94 */     filters.put("activo", "true");
/*  55: 95 */     filters.put("idOrganizacion", Integer.toString(idOrganizacion));
/*  56:    */     
/*  57: 97 */     lista = this.personaResponsableDao.obtenerListaCombo(sortField, true, filters);
/*  58:    */     
/*  59: 99 */     return lista;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public PersonaResponsable cargarDetalle(int id)
/*  63:    */   {
/*  64:104 */     return this.personaResponsableDao.cargarDetalle(id);
/*  65:    */   }
/*  66:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.datosbase.servicio.impl.ServicioPersonaResponsableImpl
 * JD-Core Version:    0.7.0.1
 */