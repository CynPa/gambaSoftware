/*   1:    */ package com.asinfo.as2.servicio.tema.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.dao.GenericoDao;
/*   4:    */ import com.asinfo.as2.entities.EntidadBase;
/*   5:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*   6:    */ import com.asinfo.as2.servicio.ServicioGenerico;
/*   7:    */ import java.util.HashMap;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.Map;
/*  10:    */ import javax.ejb.EJB;
/*  11:    */ import javax.ejb.Stateless;
/*  12:    */ 
/*  13:    */ @Stateless
/*  14:    */ public class ServicioGenericoImpl<T extends EntidadBase>
/*  15:    */   implements ServicioGenerico<T>
/*  16:    */ {
/*  17:    */   @EJB
/*  18:    */   private GenericoDao genericoDao;
/*  19:    */   @EJB
/*  20:    */   private GenericoDao<EntidadBase> detalleDao;
/*  21:    */   
/*  22:    */   public void guardar(T entidad)
/*  23:    */     throws AS2Exception
/*  24:    */   {
/*  25: 43 */     validar();
/*  26: 44 */     this.genericoDao.guardar(entidad);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public void guardarValidar(T entidad)
/*  30:    */     throws AS2Exception
/*  31:    */   {
/*  32: 49 */     validar();
/*  33: 50 */     this.genericoDao.guardarValidar(entidad);
/*  34:    */   }
/*  35:    */   
/*  36:    */   public void guardarValidar(T entidad, List<? extends EntidadBase> listaDetalles)
/*  37:    */     throws AS2Exception
/*  38:    */   {
/*  39: 55 */     validar();
/*  40:    */     
/*  41: 57 */     this.genericoDao.guardarValidar(entidad);
/*  42: 58 */     for (EntidadBase detalle : listaDetalles) {
/*  43: 59 */       this.detalleDao.guardarValidar(detalle);
/*  44:    */     }
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void eliminar(T entidad)
/*  48:    */   {
/*  49: 65 */     this.genericoDao.eliminar(entidad);
/*  50:    */   }
/*  51:    */   
/*  52:    */   public void eliminar(T entidad, List<? extends EntidadBase> listaDetalles)
/*  53:    */   {
/*  54: 70 */     for (EntidadBase detalle : listaDetalles) {
/*  55: 71 */       this.detalleDao.eliminar(detalle);
/*  56:    */     }
/*  57: 73 */     this.genericoDao.eliminar(entidad);
/*  58:    */   }
/*  59:    */   
/*  60:    */   public T buscarPorId(Class claseEntidad, Integer id)
/*  61:    */   {
/*  62: 78 */     return this.genericoDao.buscarPorId(claseEntidad, id);
/*  63:    */   }
/*  64:    */   
/*  65:    */   public List<T> obtenerListaCombo(Class claseEntidad, String sortField, boolean sortOrder, Map<String, String> filters)
/*  66:    */   {
/*  67: 83 */     return this.genericoDao.obtenerListaCombo(claseEntidad, sortField, sortOrder, filters);
/*  68:    */   }
/*  69:    */   
/*  70:    */   public List<T> obtenerListaPorPagina(Class claseEntidad, int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  71:    */   {
/*  72: 89 */     return this.genericoDao.obtenerListaPorPagina(claseEntidad, startIndex, pageSize, sortField, sortOrder, filters);
/*  73:    */   }
/*  74:    */   
/*  75:    */   public List<T> obtenerListaPorPagina(Class claseEntidad, int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters, List<String> listaCampos)
/*  76:    */   {
/*  77: 95 */     return this.genericoDao.obtenerListaPorPagina(claseEntidad, startIndex, pageSize, sortField, sortOrder, filters, listaCampos);
/*  78:    */   }
/*  79:    */   
/*  80:    */   public T cargarDetalle(Class claseEntidad, int id, List<String> listaCampos)
/*  81:    */   {
/*  82:100 */     return this.genericoDao.cargarDetalle(claseEntidad, id, listaCampos);
/*  83:    */   }
/*  84:    */   
/*  85:    */   public int contarPorCriterio(Class claseEntidad, Map<String, String> filters)
/*  86:    */   {
/*  87:105 */     return this.genericoDao.contarPorCriterio(claseEntidad, filters);
/*  88:    */   }
/*  89:    */   
/*  90:    */   public T buscarPorCodigo(Class claseEntidad, int idOrganizacion, String codigo)
/*  91:    */     throws AS2Exception
/*  92:    */   {
/*  93:110 */     Map<String, String> filtros = new HashMap();
/*  94:111 */     filtros.put("idOrganizacion", "" + idOrganizacion);
/*  95:112 */     filtros.put("codigo", "=" + codigo);
/*  96:113 */     List<T> lista = this.genericoDao.obtenerListaCombo(claseEntidad, "codigo", true, filtros);
/*  97:114 */     if (lista.size() == 1) {
/*  98:115 */       return (EntidadBase)lista.get(0);
/*  99:    */     }
/* 100:116 */     if (lista.size() > 1) {
/* 101:117 */       throw new AS2Exception("msg_error_codigo_repetido", new String[] { codigo });
/* 102:    */     }
/* 103:119 */     return null;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public T buscarPorNombre(Class claseEntidad, int idOrganizacion, String nombre)
/* 107:    */   {
/* 108:125 */     Map<String, String> filtros = new HashMap();
/* 109:126 */     filtros.put("idOrganizacion", "" + idOrganizacion);
/* 110:127 */     filtros.put("nombre", "=" + nombre);
/* 111:128 */     List<T> lista = this.genericoDao.obtenerListaCombo(claseEntidad, "nombre", true, filtros);
/* 112:129 */     if (lista.size() > 0) {
/* 113:130 */       return (EntidadBase)lista.get(0);
/* 114:    */     }
/* 115:132 */     return null;
/* 116:    */   }
/* 117:    */   
/* 118:    */   protected void validar()
/* 119:    */     throws AS2Exception
/* 120:    */   {}
/* 121:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.servicio.tema.impl.ServicioGenericoImpl
 * JD-Core Version:    0.7.0.1
 */