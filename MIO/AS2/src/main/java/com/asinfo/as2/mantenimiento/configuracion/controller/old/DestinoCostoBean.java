/*   1:    */ package com.asinfo.as2.mantenimiento.configuracion.controller.old;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.controller.LanguageController;
/*   4:    */ import com.asinfo.as2.controller.PageControllerAS2;
/*   5:    */ import com.asinfo.as2.entities.DestinoCosto;
/*   6:    */ import com.asinfo.as2.entities.Organizacion;
/*   7:    */ import com.asinfo.as2.entities.Sucursal;
/*   8:    */ import com.asinfo.as2.mantenimiento.configuracion.old.ServicioDestinoCosto;
/*   9:    */ import com.asinfo.as2.util.AppUtil;
/*  10:    */ import java.util.ArrayList;
/*  11:    */ import java.util.List;
/*  12:    */ import java.util.Map;
/*  13:    */ import javax.annotation.PostConstruct;
/*  14:    */ import javax.ejb.EJB;
/*  15:    */ import javax.faces.bean.ManagedBean;
/*  16:    */ import javax.faces.bean.ViewScoped;
/*  17:    */ import org.apache.log4j.Logger;
/*  18:    */ import org.primefaces.component.datatable.DataTable;
/*  19:    */ import org.primefaces.model.LazyDataModel;
/*  20:    */ import org.primefaces.model.SortOrder;
/*  21:    */ 
/*  22:    */ @ManagedBean
/*  23:    */ @ViewScoped
/*  24:    */ public class DestinoCostoBean
/*  25:    */   extends PageControllerAS2
/*  26:    */ {
/*  27:    */   private static final long serialVersionUID = -2518505713762501239L;
/*  28:    */   @EJB
/*  29:    */   private ServicioDestinoCosto servicioDestinoCosto;
/*  30:    */   private DestinoCosto destinoCosto;
/*  31:    */   private LazyDataModel<DestinoCosto> listaDestinoCosto;
/*  32:    */   private DataTable dtDestinoCosto;
/*  33:    */   
/*  34:    */   @PostConstruct
/*  35:    */   public void init()
/*  36:    */   {
/*  37: 77 */     this.listaDestinoCosto = new LazyDataModel()
/*  38:    */     {
/*  39:    */       private static final long serialVersionUID = 1L;
/*  40:    */       
/*  41:    */       public List<DestinoCosto> load(int startIndex, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters)
/*  42:    */       {
/*  43: 84 */         List<DestinoCosto> lista = new ArrayList();
/*  44: 85 */         boolean ordenar = sortOrder == SortOrder.ASCENDING;
/*  45:    */         
/*  46: 87 */         filters.put("idOrganizacion", "" + AppUtil.getOrganizacion().getIdOrganizacion());
/*  47: 88 */         lista = DestinoCostoBean.this.servicioDestinoCosto.obtenerListaPorPagina(startIndex, pageSize, sortField, ordenar, filters);
/*  48:    */         
/*  49: 90 */         DestinoCostoBean.this.listaDestinoCosto.setRowCount(DestinoCostoBean.this.servicioDestinoCosto.contarPorCriterio(filters));
/*  50:    */         
/*  51: 92 */         return lista;
/*  52:    */       }
/*  53:    */     };
/*  54:    */   }
/*  55:    */   
/*  56:    */   private void crearEntidad()
/*  57:    */   {
/*  58:110 */     this.destinoCosto = new DestinoCosto();
/*  59:111 */     this.destinoCosto.setIdOrganizacion(AppUtil.getOrganizacion().getIdOrganizacion());
/*  60:112 */     this.destinoCosto.setIdSucursal(AppUtil.getSucursal().getIdSucursal());
/*  61:113 */     this.destinoCosto.setActivo(true);
/*  62:    */   }
/*  63:    */   
/*  64:    */   public String editar()
/*  65:    */   {
/*  66:122 */     if (getDestinoCosto().getIdDestinoCosto() > 0) {
/*  67:124 */       setEditado(true);
/*  68:    */     } else {
/*  69:126 */       addInfoMessage(getLanguageController().getMensaje("msg_info_seleccionar"));
/*  70:    */     }
/*  71:128 */     return "";
/*  72:    */   }
/*  73:    */   
/*  74:    */   public String guardar()
/*  75:    */   {
/*  76:    */     try
/*  77:    */     {
/*  78:138 */       this.servicioDestinoCosto.guardar(this.destinoCosto);
/*  79:139 */       addInfoMessage(getLanguageController().getMensaje("msg_info_guardar"));
/*  80:140 */       limpiar();
/*  81:141 */       setEditado(false);
/*  82:    */     }
/*  83:    */     catch (Exception e)
/*  84:    */     {
/*  85:143 */       addErrorMessage(getLanguageController().getMensaje("msg_error_guardar"));
/*  86:144 */       LOG.error("ERROR AL GUARDAR DATOS", e);
/*  87:    */     }
/*  88:146 */     return "";
/*  89:    */   }
/*  90:    */   
/*  91:    */   public String eliminar()
/*  92:    */   {
/*  93:    */     try
/*  94:    */     {
/*  95:156 */       this.servicioDestinoCosto.eliminar(this.destinoCosto);
/*  96:157 */       addInfoMessage(getLanguageController().getMensaje("msg_info_eliminar"));
/*  97:    */     }
/*  98:    */     catch (Exception e)
/*  99:    */     {
/* 100:159 */       addErrorMessage(getLanguageController().getMensaje("msg_error_eliminar"));
/* 101:160 */       LOG.error("ERROR AL ELIMINAR DATOS", e);
/* 102:    */     }
/* 103:162 */     return "";
/* 104:    */   }
/* 105:    */   
/* 106:    */   public String cargarDatos()
/* 107:    */   {
/* 108:171 */     return "";
/* 109:    */   }
/* 110:    */   
/* 111:    */   public String limpiar()
/* 112:    */   {
/* 113:180 */     crearEntidad();
/* 114:181 */     return "";
/* 115:    */   }
/* 116:    */   
/* 117:    */   public DestinoCosto getDestinoCosto()
/* 118:    */   {
/* 119:194 */     return this.destinoCosto;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void setDestinoCosto(DestinoCosto destinoCosto)
/* 123:    */   {
/* 124:204 */     this.destinoCosto = destinoCosto;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public LazyDataModel<DestinoCosto> getListaDestinoCosto()
/* 128:    */   {
/* 129:213 */     return this.listaDestinoCosto;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public void setListaDestinoCosto(LazyDataModel<DestinoCosto> listaDestinoCosto)
/* 133:    */   {
/* 134:223 */     this.listaDestinoCosto = listaDestinoCosto;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public DataTable getDtDestinoCosto()
/* 138:    */   {
/* 139:232 */     return this.dtDestinoCosto;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void setDtDestinoCosto(DataTable dtDestinoCosto)
/* 143:    */   {
/* 144:242 */     this.dtDestinoCosto = dtDestinoCosto;
/* 145:    */   }
/* 146:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.mantenimiento.configuracion.controller.old.DestinoCostoBean
 * JD-Core Version:    0.7.0.1
 */