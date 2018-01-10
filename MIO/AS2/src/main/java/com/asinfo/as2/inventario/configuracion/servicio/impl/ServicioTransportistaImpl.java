/*   1:    */ package com.asinfo.as2.inventario.configuracion.servicio.impl;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.configuracionbase.servicio.ServicioTipoIdentificacion;
/*   4:    */ import com.asinfo.as2.dao.TransportistaDao;
/*   5:    */ import com.asinfo.as2.entities.DireccionEmpresa;
/*   6:    */ import com.asinfo.as2.entities.Empresa;
/*   7:    */ import com.asinfo.as2.entities.TipoIdentificacion;
/*   8:    */ import com.asinfo.as2.entities.Transportista;
/*   9:    */ import com.asinfo.as2.entities.Zona;
/*  10:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  11:    */ import com.asinfo.as2.excepciones.AS2ExceptionMantenimiento;
/*  12:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioTransportista;
/*  13:    */ import com.asinfo.as2.servicio.AbstractServicioAS2;
/*  14:    */ import java.util.Date;
/*  15:    */ import java.util.HashMap;
/*  16:    */ import java.util.List;
/*  17:    */ import java.util.Map;
/*  18:    */ import javax.ejb.EJB;
/*  19:    */ import javax.ejb.SessionContext;
/*  20:    */ import javax.ejb.Stateless;
/*  21:    */ 
/*  22:    */ @Stateless
/*  23:    */ public class ServicioTransportistaImpl
/*  24:    */   extends AbstractServicioAS2
/*  25:    */   implements ServicioTransportista
/*  26:    */ {
/*  27:    */   private static final long serialVersionUID = 7805398966383409001L;
/*  28:    */   @EJB
/*  29:    */   private TransportistaDao transportistaDao;
/*  30:    */   @EJB
/*  31:    */   private ServicioTipoIdentificacion servicioTipoIdentificacion;
/*  32:    */   
/*  33:    */   public void guardar(Transportista transportista)
/*  34:    */   {
/*  35: 53 */     transportista.setTipoIdentificacion(this.servicioTipoIdentificacion.buscarPorId(Integer.valueOf(transportista.getTipoIdentificacion().getId())));
/*  36: 54 */     this.transportistaDao.guardar(transportista);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public void eliminar(Transportista transportista)
/*  40:    */   {
/*  41: 65 */     this.transportistaDao.eliminar(transportista);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public Transportista buscarPorId(Integer id)
/*  45:    */   {
/*  46: 76 */     return (Transportista)this.transportistaDao.buscarPorId(id);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public List<Transportista> obtenerListaPorPagina(int startIndex, int pageSize, String sortField, boolean sortOrder, Map<String, String> filters)
/*  50:    */   {
/*  51: 88 */     return this.transportistaDao.obtenerListaPorPagina(startIndex, pageSize, sortField, sortOrder, filters);
/*  52:    */   }
/*  53:    */   
/*  54:    */   public List<Transportista> obtenerListaCombo(String sortField, boolean sortOrder, Map<String, String> filters)
/*  55:    */   {
/*  56: 98 */     return this.transportistaDao.obtenerListaCombo(sortField, sortOrder, filters);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public int contarPorCriterio(Map<String, String> filters)
/*  60:    */   {
/*  61:108 */     return this.transportistaDao.contarPorCriterio(filters);
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void actualizarEmpresaTransportista(Empresa empresa)
/*  65:    */   {
/*  66:113 */     Map<String, String> filters = new HashMap();
/*  67:114 */     filters.put("empresa.idEmpresa", "" + empresa.getId());
/*  68:115 */     List<Transportista> listaTransportistasAsociados = obtenerListaCombo("nombre", true, filters);
/*  69:116 */     for (Transportista transportista : listaTransportistasAsociados)
/*  70:    */     {
/*  71:117 */       transportista.setTipoIdentificacion(transportista.getEmpresa().getTipoIdentificacion());
/*  72:118 */       transportista.setIdentificacion(transportista.getEmpresa().getIdentificacion());
/*  73:119 */       transportista.setNombre(transportista.getEmpresa().getNombreFiscal());
/*  74:120 */       transportista.setEmail(transportista.getEmpresa().getEmail1());
/*  75:123 */       for (DireccionEmpresa direccionEmpresa : empresa.getDirecciones())
/*  76:    */       {
/*  77:124 */         transportista.setTelefono(direccionEmpresa.getTelefono1());
/*  78:125 */         transportista.setDireccion(direccionEmpresa.getDireccionCompleta());
/*  79:126 */         if (direccionEmpresa.isIndicadorDireccionPrincipal()) {
/*  80:    */           break;
/*  81:    */         }
/*  82:    */       }
/*  83:130 */       guardar(transportista);
/*  84:    */     }
/*  85:    */   }
/*  86:    */   
/*  87:    */   public boolean verificarTransportista(int idOrganizacion, String nombreUsuario)
/*  88:    */   {
/*  89:137 */     boolean resultado = false;
/*  90:138 */     Transportista transportista = this.transportistaDao.verificarTransportista(idOrganizacion, nombreUsuario);
/*  91:139 */     if (transportista != null) {
/*  92:140 */       resultado = true;
/*  93:    */     }
/*  94:142 */     return resultado;
/*  95:    */   }
/*  96:    */   
/*  97:    */   public Transportista actualizarTransportista(int idOrganizacion, String nombreUsuario)
/*  98:    */   {
/*  99:147 */     return this.transportistaDao.verificarTransportista(idOrganizacion, nombreUsuario);
/* 100:    */   }
/* 101:    */   
/* 102:    */   public void actuzalizaTransportista(Date fecha, List<Zona> listaZona, Transportista transportista, boolean actualizarTransportista)
/* 103:    */     throws AS2Exception
/* 104:    */   {
/* 105:    */     try
/* 106:    */     {
/* 107:153 */       if (!listaZona.isEmpty()) {
/* 108:154 */         this.transportistaDao.actuzalizaTransportista(fecha, listaZona, transportista, actualizarTransportista);
/* 109:    */       } else {
/* 110:156 */         throw new AS2Exception("msg_error_seleccione_zona", new String[] { "" });
/* 111:    */       }
/* 112:    */     }
/* 113:    */     catch (Exception e)
/* 114:    */     {
/* 115:159 */       this.context.setRollbackOnly();
/* 116:160 */       e.printStackTrace();
/* 117:161 */       throw new AS2ExceptionMantenimiento(e.getMessage());
/* 118:    */     }
/* 119:    */   }
/* 120:    */   
/* 121:    */   public List<Zona> obtenerZonaAsignada(int idTransportista)
/* 122:    */   {
/* 123:167 */     return this.transportistaDao.obtenerZonaAsignada(idTransportista);
/* 124:    */   }
/* 125:    */   
/* 126:    */   public Transportista cargarDetalle(int idTransportista)
/* 127:    */   {
/* 128:172 */     return this.transportistaDao.cargarDetalle(idTransportista);
/* 129:    */   }
/* 130:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.configuracion.servicio.impl.ServicioTransportistaImpl
 * JD-Core Version:    0.7.0.1
 */