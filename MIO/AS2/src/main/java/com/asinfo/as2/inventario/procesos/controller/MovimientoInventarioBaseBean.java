/*   1:    */ package com.asinfo.as2.inventario.procesos.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.datosbase.servicio.ServicioEmpresa;
/*   6:    */ import com.asinfo.as2.datosbase.servicio.ServicioPersonaResponsable;
/*   7:    */ import com.asinfo.as2.entities.MotivoAjusteInventario;
/*   8:    */ import com.asinfo.as2.entities.MovimientoInventario;
/*   9:    */ import com.asinfo.as2.entities.Organizacion;
/*  10:    */ import com.asinfo.as2.entities.PersonaResponsable;
/*  11:    */ import com.asinfo.as2.entities.Producto;
/*  12:    */ import com.asinfo.as2.entities.SaldoProductoLote;
/*  13:    */ import com.asinfo.as2.excepciones.AS2Exception;
/*  14:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  15:    */ import com.asinfo.as2.inventario.configuracion.controller.ListaProductoBean;
/*  16:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioMotivoAjusteInventario;
/*  17:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioProducto;
/*  18:    */ import com.asinfo.as2.inventario.procesos.servicio.ServicioMovimientoInventario;
/*  19:    */ import com.asinfo.as2.util.AppUtil;
/*  20:    */ import com.asinfo.as2.utils.JsfUtil;
/*  21:    */ import java.util.ArrayList;
/*  22:    */ import java.util.HashMap;
/*  23:    */ import java.util.List;
/*  24:    */ import java.util.Map;
/*  25:    */ import javax.ejb.EJB;
/*  26:    */ import javax.faces.bean.ManagedProperty;
/*  27:    */ 
/*  28:    */ public abstract class MovimientoInventarioBaseBean
/*  29:    */   extends PageControllerAS2
/*  30:    */ {
/*  31:    */   private static final long serialVersionUID = 1L;
/*  32:    */   @EJB
/*  33:    */   private ServicioMotivoAjusteInventario servicioMotivoAjusteInventario;
/*  34:    */   @EJB
/*  35:    */   private ServicioPersonaResponsable servicioResponsableSalidaMercaderia;
/*  36:    */   @EJB
/*  37:    */   protected transient ServicioEmpresa servicioEmpresa;
/*  38:    */   @EJB
/*  39:    */   protected transient ServicioMovimientoInventario servicioMovimientoInventario;
/*  40:    */   @EJB
/*  41:    */   private transient ServicioProducto servicioProducto;
/*  42:    */   @ManagedProperty("#{listaProductoBean}")
/*  43:    */   private ListaProductoBean listaProductoBean;
/*  44:    */   private List<PersonaResponsable> listaResponsableSalidaMercaderia;
/*  45:    */   
/*  46:    */   public abstract void cargarProducto();
/*  47:    */   
/*  48:    */   public ListaProductoBean getListaProductoBean()
/*  49:    */   {
/*  50: 71 */     return this.listaProductoBean;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public void setListaProductoBean(ListaProductoBean listaProductoBean)
/*  54:    */   {
/*  55: 75 */     this.listaProductoBean = listaProductoBean;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public void cargarProductoLote(SaldoProductoLote saldoLote)
/*  59:    */   {
/*  60: 79 */     if (saldoLote.getProducto().equals(getListaProductoBean().getProducto()))
/*  61:    */     {
/*  62: 80 */       getListaProductoBean().setSaldoProductoLote(saldoLote);
/*  63: 81 */       cargarProducto();
/*  64:    */     }
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void cargarProducto(Producto producto)
/*  68:    */   {
/*  69: 86 */     getListaProductoBean().setProducto(producto);
/*  70: 87 */     getListaProductoBean().setSaldoProductoLote(null);
/*  71: 88 */     cargarProducto();
/*  72:    */   }
/*  73:    */   
/*  74:    */   public String contabilizar(MovimientoInventario movimientoInventario)
/*  75:    */   {
/*  76: 93 */     movimientoInventario = this.servicioMovimientoInventario.cargarDetalle(Integer.valueOf(movimientoInventario.getId()));
/*  77:    */     try
/*  78:    */     {
/*  79: 96 */       this.servicioMovimientoInventario.esEditable(movimientoInventario);
/*  80: 97 */       this.servicioMovimientoInventario.contabilizar(movimientoInventario);
/*  81:    */       
/*  82: 99 */       addInfoMessage(getLanguageController().getMensaje("msg_info_proceso"));
/*  83:    */     }
/*  84:    */     catch (ExcepcionAS2 e)
/*  85:    */     {
/*  86:102 */       addInfoMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + " " + e.getMessage());
/*  87:    */     }
/*  88:    */     catch (AS2Exception e)
/*  89:    */     {
/*  90:104 */       JsfUtil.addErrorMessage(e, "");
/*  91:    */     }
/*  92:    */     catch (Exception e)
/*  93:    */     {
/*  94:106 */       addErrorMessage(getLanguageController().getMensaje("msg_proceso_erroneo"));
/*  95:107 */       e.printStackTrace();
/*  96:    */     }
/*  97:110 */     return "";
/*  98:    */   }
/*  99:    */   
/* 100:    */   public List<MotivoAjusteInventario> autocompletarMotivoAjusteInventario(String consulta)
/* 101:    */   {
/* 102:122 */     consulta = consulta.toUpperCase();
/* 103:123 */     return this.servicioMotivoAjusteInventario.autoCompletarMotivoAjusteInventario(consulta);
/* 104:    */   }
/* 105:    */   
/* 106:    */   public List<PersonaResponsable> autocompletarResponsable(String consulta)
/* 107:    */   {
/* 108:132 */     consulta = consulta.toUpperCase();
/* 109:    */     
/* 110:134 */     List<PersonaResponsable> lista = new ArrayList();
/* 111:136 */     for (PersonaResponsable rsm : getListaResponsableSalidaMercaderia()) {
/* 112:137 */       if ((rsm.getNombres().toUpperCase().startsWith(consulta)) || (rsm.getApellidos().toUpperCase().startsWith(consulta)) || 
/* 113:138 */         (rsm.getIdentificacion().startsWith(consulta))) {
/* 114:139 */         lista.add(rsm);
/* 115:    */       }
/* 116:    */     }
/* 117:143 */     return lista;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public List<PersonaResponsable> getListaResponsableSalidaMercaderia()
/* 121:    */   {
/* 122:152 */     if (this.listaResponsableSalidaMercaderia == null)
/* 123:    */     {
/* 124:153 */       Map<String, String> filtros = agregarFiltroOrganizacion(null);
/* 125:154 */       filtros.put("indicadorSalidaMercaderia", "true");
/* 126:155 */       this.listaResponsableSalidaMercaderia = this.servicioResponsableSalidaMercaderia.obtenerListaCombo("nombres", true, filtros);
/* 127:    */     }
/* 128:158 */     return this.listaResponsableSalidaMercaderia;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public List<Producto> autocompletarProductos(String consulta)
/* 132:    */   {
/* 133:162 */     Map<String, String> filtros = new HashMap();
/* 134:163 */     return this.servicioProducto.autocompletarProductos(Integer.valueOf(AppUtil.getOrganizacion().getId()), consulta, filtros);
/* 135:    */   }
/* 136:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.procesos.controller.MovimientoInventarioBaseBean
 * JD-Core Version:    0.7.0.1
 */