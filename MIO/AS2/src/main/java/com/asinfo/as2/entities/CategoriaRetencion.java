/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.List;
/*   5:    */ import javax.persistence.Column;
/*   6:    */ import javax.persistence.Entity;
/*   7:    */ import javax.persistence.FetchType;
/*   8:    */ import javax.persistence.GeneratedValue;
/*   9:    */ import javax.persistence.GenerationType;
/*  10:    */ import javax.persistence.Id;
/*  11:    */ import javax.persistence.OneToMany;
/*  12:    */ import javax.persistence.Table;
/*  13:    */ import javax.persistence.TableGenerator;
/*  14:    */ import javax.validation.constraints.NotNull;
/*  15:    */ import javax.validation.constraints.Size;
/*  16:    */ 
/*  17:    */ @Entity
/*  18:    */ @Table(name="categoria_retencion")
/*  19:    */ public class CategoriaRetencion
/*  20:    */   extends EntidadBase
/*  21:    */ {
/*  22:    */   private static final long serialVersionUID = 3122157129916743983L;
/*  23:    */   @Id
/*  24:    */   @TableGenerator(name="categoria_retencion", initialValue=0, allocationSize=50)
/*  25:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="categoria_retencion")
/*  26:    */   @Column(name="id_categoria_retencion", unique=true, nullable=false)
/*  27:    */   private int idCategoriaRetencion;
/*  28:    */   @Column(name="id_organizacion", nullable=false)
/*  29:    */   private int idOrganizacion;
/*  30:    */   @Column(name="id_sucursal", nullable=false)
/*  31:    */   private int idSucursal;
/*  32:    */   @Column(name="codigo", nullable=false, length=10)
/*  33:    */   @NotNull
/*  34:    */   @Size(min=2, max=10)
/*  35:    */   private String codigo;
/*  36:    */   @Column(name="nombre", nullable=false, length=50)
/*  37:    */   @NotNull
/*  38:    */   @Size(min=2, max=50)
/*  39:    */   private String nombre;
/*  40:    */   @Column(name="descripcion", length=200)
/*  41:    */   @Size(max=200)
/*  42:    */   private String descripcion;
/*  43:    */   @Column(name="activo", nullable=false)
/*  44:    */   private boolean activo;
/*  45:    */   @Column(name="predeterminado", nullable=false)
/*  46:    */   private boolean predeterminado;
/*  47:    */   @Column(name="indicador_valor_retenido_bienes", nullable=false)
/*  48:    */   private boolean indicadorValorRetenidoBienes;
/*  49:    */   @Column(name="indicador_valor_retenido_servicios", nullable=false)
/*  50:    */   private boolean indicadorValorRetenidoServicios;
/*  51:    */   @Column(name="indicador_valor_retenido_bienes_servicios", nullable=false)
/*  52:    */   private boolean indicadorValorRetenidoBienesServicios;
/*  53:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="categoriaRetencion")
/*  54: 81 */   private List<DetalleCategoriaRetencion> listaDetalleCategoriaRetencion = new ArrayList();
/*  55:    */   
/*  56:    */   public CategoriaRetencion() {}
/*  57:    */   
/*  58:    */   public CategoriaRetencion(int idCategoriaRetencion, String codigo, String nombre)
/*  59:    */   {
/*  60:100 */     this.idCategoriaRetencion = idCategoriaRetencion;
/*  61:101 */     this.codigo = codigo;
/*  62:102 */     this.nombre = nombre;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public int getId()
/*  66:    */   {
/*  67:112 */     return this.idCategoriaRetencion;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public int getIdCategoriaRetencion()
/*  71:    */   {
/*  72:123 */     return this.idCategoriaRetencion;
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void setIdCategoriaRetencion(int idCategoriaRetencion)
/*  76:    */   {
/*  77:133 */     this.idCategoriaRetencion = idCategoriaRetencion;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public int getIdOrganizacion()
/*  81:    */   {
/*  82:142 */     return this.idOrganizacion;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void setIdOrganizacion(int idOrganizacion)
/*  86:    */   {
/*  87:152 */     this.idOrganizacion = idOrganizacion;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public int getIdSucursal()
/*  91:    */   {
/*  92:161 */     return this.idSucursal;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void setIdSucursal(int idSucursal)
/*  96:    */   {
/*  97:171 */     this.idSucursal = idSucursal;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public String getCodigo()
/* 101:    */   {
/* 102:180 */     return this.codigo;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void setCodigo(String codigo)
/* 106:    */   {
/* 107:190 */     this.codigo = codigo;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public String getNombre()
/* 111:    */   {
/* 112:199 */     return this.nombre;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void setNombre(String nombre)
/* 116:    */   {
/* 117:209 */     this.nombre = nombre;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public String getDescripcion()
/* 121:    */   {
/* 122:218 */     return this.descripcion;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void setDescripcion(String descripcion)
/* 126:    */   {
/* 127:228 */     this.descripcion = descripcion;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public boolean isActivo()
/* 131:    */   {
/* 132:237 */     return this.activo;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void setActivo(boolean activo)
/* 136:    */   {
/* 137:247 */     this.activo = activo;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public boolean isPredeterminado()
/* 141:    */   {
/* 142:256 */     return this.predeterminado;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public void setPredeterminado(boolean predeterminado)
/* 146:    */   {
/* 147:266 */     this.predeterminado = predeterminado;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public List<DetalleCategoriaRetencion> getListaDetalleCategoriaRetencion()
/* 151:    */   {
/* 152:275 */     return this.listaDetalleCategoriaRetencion;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void setListaDetalleCategoriaRetencion(List<DetalleCategoriaRetencion> listaDetalleCategoriaRetencion)
/* 156:    */   {
/* 157:285 */     this.listaDetalleCategoriaRetencion = listaDetalleCategoriaRetencion;
/* 158:    */   }
/* 159:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.CategoriaRetencion
 * JD-Core Version:    0.7.0.1
 */