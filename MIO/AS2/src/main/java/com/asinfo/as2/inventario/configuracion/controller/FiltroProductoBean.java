/*   1:    */ package com.asinfo.as2.inventario.configuracion.controller;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.Atributo;
/*   6:    */ import com.asinfo.as2.entities.FiltroProducto;
/*   7:    */ import com.asinfo.as2.entities.Organizacion;
/*   8:    */ import com.asinfo.as2.entities.Sucursal;
/*   9:    */ import com.asinfo.as2.excepciones.ExcepcionAS2;
/*  10:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioAtributo;
/*  11:    */ import com.asinfo.as2.inventario.configuracion.servicio.ServicioFiltroProducto;
/*  12:    */ import com.asinfo.as2.util.AppUtil;
/*  13:    */ import java.util.List;
/*  14:    */ import java.util.Map;
/*  15:    */ import javax.annotation.PostConstruct;
/*  16:    */ import javax.ejb.EJB;
/*  17:    */ import javax.faces.bean.ManagedBean;
/*  18:    */ import javax.faces.bean.ViewScoped;
/*  19:    */ import org.apache.log4j.Logger;
/*  20:    */ import org.primefaces.component.datatable.DataTable;
/*  21:    */ import org.primefaces.model.LazyDataModel;
/*  22:    */ 
/*  23:    */ @ManagedBean
/*  24:    */ @ViewScoped
/*  25:    */ public class FiltroProductoBean
/*  26:    */   extends PageControllerAS2
/*  27:    */ {
/*  28:    */   private static final long serialVersionUID = -2518505713762501239L;
/*  29:    */   @EJB
/*  30:    */   private ServicioFiltroProducto servicioFiltroProducto;
/*  31:    */   @EJB
/*  32:    */   private ServicioAtributo servicioAtributo;
/*  33:    */   private FiltroProducto filtroProducto;
/*  34:    */   private LazyDataModel<FiltroProducto> listaFiltroProducto;
/*  35:    */   private List<Atributo> listaAtributo;
/*  36:    */   private DataTable dtFiltroProducto;
/*  37:    */   
/*  38:    */   @PostConstruct
/*  39:    */   public void init()
/*  40:    */   {
/*  41: 79 */     List<FiltroProducto> lista = this.servicioFiltroProducto.obtenerListaCombo("", false, null);
/*  42: 81 */     if (lista.isEmpty()) {
/*  43: 82 */       crearEntidad();
/*  44:    */     } else {
/*  45: 84 */       this.filtroProducto = ((FiltroProducto)lista.get(0));
/*  46:    */     }
/*  47:    */   }
/*  48:    */   
/*  49:    */   private void crearEntidad()
/*  50:    */   {
/*  51:100 */     this.filtroProducto = new FiltroProducto();
/*  52:101 */     this.filtroProducto.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/*  53:102 */     this.filtroProducto.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/*  54:103 */     this.filtroProducto.setIndicadorCodigo(true);
/*  55:104 */     this.filtroProducto.setIndicadorCodigoAlterno(true);
/*  56:105 */     this.filtroProducto.setIndicadorNombre(true);
/*  57:106 */     this.filtroProducto.setIndicadorNombreComercial(true);
/*  58:107 */     this.filtroProducto.setIndicadorCategoriaProducto(true);
/*  59:108 */     this.filtroProducto.setIndicadorSubcategoriaProducto(true);
/*  60:109 */     this.filtroProducto.setIndicadorUnidad(true);
/*  61:    */   }
/*  62:    */   
/*  63:    */   public String editar()
/*  64:    */   {
/*  65:118 */     return "";
/*  66:    */   }
/*  67:    */   
/*  68:    */   public String guardar()
/*  69:    */   {
/*  70:    */     try
/*  71:    */     {
/*  72:128 */       this.servicioFiltroProducto.guardar(getFiltroProducto());
/*  73:129 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  74:    */     }
/*  75:    */     catch (ExcepcionAS2 e)
/*  76:    */     {
/*  77:131 */       addErrorMessage(getLanguageController().getMensaje(e.getCodigoExcepcion()) + e.getMessage());
/*  78:132 */       LOG.error("ERROR AL GUARDAR DATOS FACTURA CLIENTE", e);
/*  79:133 */       e.printStackTrace();
/*  80:    */     }
/*  81:    */     catch (Exception e)
/*  82:    */     {
/*  83:135 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  84:136 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  85:    */     }
/*  86:138 */     return "";
/*  87:    */   }
/*  88:    */   
/*  89:    */   public String eliminar()
/*  90:    */   {
/*  91:147 */     return "";
/*  92:    */   }
/*  93:    */   
/*  94:    */   public String cargarDatos()
/*  95:    */   {
/*  96:156 */     return "";
/*  97:    */   }
/*  98:    */   
/*  99:    */   public String limpiar()
/* 100:    */   {
/* 101:165 */     return "";
/* 102:    */   }
/* 103:    */   
/* 104:    */   public FiltroProducto getFiltroProducto()
/* 105:    */   {
/* 106:178 */     if (this.filtroProducto == null) {
/* 107:179 */       this.filtroProducto = new FiltroProducto();
/* 108:    */     }
/* 109:181 */     return this.filtroProducto;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public void setFiltroProducto(FiltroProducto filtroProducto)
/* 113:    */   {
/* 114:191 */     this.filtroProducto = filtroProducto;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public LazyDataModel<FiltroProducto> getListaFiltroProducto()
/* 118:    */   {
/* 119:200 */     return this.listaFiltroProducto;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void setListaFiltroProducto(LazyDataModel<FiltroProducto> listaFiltroProducto)
/* 123:    */   {
/* 124:210 */     this.listaFiltroProducto = listaFiltroProducto;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public List<Atributo> getListaAtributo()
/* 128:    */   {
/* 129:219 */     if (this.listaAtributo == null)
/* 130:    */     {
/* 131:220 */       Map<String, String> filters = agregarFiltroOrganizacion(null);
/* 132:221 */       filters.put("indicadorProducto", "true");
/* 133:222 */       this.listaAtributo = this.servicioAtributo.obtenerListaCombo("nombre", true, filters);
/* 134:    */     }
/* 135:224 */     return this.listaAtributo;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public void setListaAtributo(List<Atributo> listaAtributo)
/* 139:    */   {
/* 140:234 */     this.listaAtributo = listaAtributo;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public DataTable getDtFiltroProducto()
/* 144:    */   {
/* 145:243 */     return this.dtFiltroProducto;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public void setDtFiltroProducto(DataTable dtFiltroProducto)
/* 149:    */   {
/* 150:253 */     this.dtFiltroProducto = dtFiltroProducto;
/* 151:    */   }
/* 152:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.inventario.configuracion.controller.FiltroProductoBean
 * JD-Core Version:    0.7.0.1
 */