/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.utils.FuncionesUtiles;
/*   4:    */ import java.util.ArrayList;
/*   5:    */ import java.util.Date;
/*   6:    */ import java.util.List;
/*   7:    */ import javax.persistence.Column;
/*   8:    */ import javax.persistence.Entity;
/*   9:    */ import javax.persistence.FetchType;
/*  10:    */ import javax.persistence.GeneratedValue;
/*  11:    */ import javax.persistence.GenerationType;
/*  12:    */ import javax.persistence.Id;
/*  13:    */ import javax.persistence.OneToMany;
/*  14:    */ import javax.persistence.Table;
/*  15:    */ import javax.persistence.TableGenerator;
/*  16:    */ import javax.validation.constraints.Min;
/*  17:    */ import javax.validation.constraints.NotNull;
/*  18:    */ import javax.validation.constraints.Size;
/*  19:    */ 
/*  20:    */ @Entity
/*  21:    */ @Table(name="rango_costo_estandar_producto", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "anio", "mes"})})
/*  22:    */ public class RangoCostoEstandarProducto
/*  23:    */   extends EntidadBase
/*  24:    */ {
/*  25:    */   private static final long serialVersionUID = 1L;
/*  26:    */   @Id
/*  27:    */   @TableGenerator(name="rango_costo_estandar_producto", initialValue=0, allocationSize=50)
/*  28:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="rango_costo_estandar_producto")
/*  29:    */   @Column(name="id_rango_costo_estandar_producto")
/*  30:    */   private int idRangoCostoEstandarProducto;
/*  31:    */   @Column(name="id_organizacion", nullable=false)
/*  32:    */   private int idOrganizacion;
/*  33:    */   @Column(name="id_sucursal", nullable=false)
/*  34:    */   private int idSucursal;
/*  35:    */   @Column(name="codigo", length=10, nullable=false)
/*  36:    */   @NotNull
/*  37:    */   @Size(max=10)
/*  38:    */   private String codigo;
/*  39:    */   @Column(name="nombre", length=50, nullable=false)
/*  40:    */   @NotNull
/*  41:    */   @Size(max=50)
/*  42:    */   private String nombre;
/*  43:    */   @Column(name="descripcion", length=200)
/*  44:    */   @Size(max=200)
/*  45:    */   private String descripcion;
/*  46:    */   @Column(name="activo", nullable=false)
/*  47:    */   @NotNull
/*  48:    */   private boolean activo;
/*  49:    */   @Column(name="anio", nullable=false)
/*  50:    */   @Min(0L)
/*  51: 77 */   private int anio = FuncionesUtiles.getAnio(new Date());
/*  52:    */   @Column(name="mes", nullable=false)
/*  53:    */   @Min(0L)
/*  54:    */   private int mes;
/*  55:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="rangoCostoEstandarProducto")
/*  56: 83 */   private List<CostoEstandarProducto> listaCostoEstandarProducto = new ArrayList();
/*  57:    */   
/*  58:    */   public int getId()
/*  59:    */   {
/*  60: 93 */     return this.idRangoCostoEstandarProducto;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public int getIdRangoCostoEstandarProducto()
/*  64:    */   {
/*  65:102 */     return this.idRangoCostoEstandarProducto;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void setIdRangoCostoEstandarProducto(int idRangoCostoEstandarProducto)
/*  69:    */   {
/*  70:112 */     this.idRangoCostoEstandarProducto = idRangoCostoEstandarProducto;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public int getIdOrganizacion()
/*  74:    */   {
/*  75:121 */     return this.idOrganizacion;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void setIdOrganizacion(int idOrganizacion)
/*  79:    */   {
/*  80:131 */     this.idOrganizacion = idOrganizacion;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public int getIdSucursal()
/*  84:    */   {
/*  85:140 */     return this.idSucursal;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void setIdSucursal(int idSucursal)
/*  89:    */   {
/*  90:150 */     this.idSucursal = idSucursal;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public String getCodigo()
/*  94:    */   {
/*  95:159 */     return this.codigo;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void setCodigo(String codigo)
/*  99:    */   {
/* 100:169 */     this.codigo = codigo;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public String getNombre()
/* 104:    */   {
/* 105:178 */     return this.nombre;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void setNombre(String nombre)
/* 109:    */   {
/* 110:188 */     this.nombre = nombre;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public String getDescripcion()
/* 114:    */   {
/* 115:197 */     return this.descripcion;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void setDescripcion(String descripcion)
/* 119:    */   {
/* 120:207 */     this.descripcion = descripcion;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public boolean isActivo()
/* 124:    */   {
/* 125:216 */     return this.activo;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void setActivo(boolean activo)
/* 129:    */   {
/* 130:226 */     this.activo = activo;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public int getAnio()
/* 134:    */   {
/* 135:235 */     return this.anio;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public void setAnio(int anio)
/* 139:    */   {
/* 140:245 */     this.anio = anio;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public int getMes()
/* 144:    */   {
/* 145:254 */     return this.mes;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public void setMes(int mes)
/* 149:    */   {
/* 150:264 */     this.mes = mes;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public List<CostoEstandarProducto> getListaCostoEstandarProducto()
/* 154:    */   {
/* 155:273 */     return this.listaCostoEstandarProducto;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public void setListaCostoEstandarProducto(List<CostoEstandarProducto> listaCostoEstandarProducto)
/* 159:    */   {
/* 160:283 */     this.listaCostoEstandarProducto = listaCostoEstandarProducto;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public void setEliminado(boolean eliminado)
/* 164:    */   {
/* 165:293 */     for (CostoEstandarProducto costoEstandarProducto : this.listaCostoEstandarProducto) {
/* 166:294 */       costoEstandarProducto.setEliminado(true);
/* 167:    */     }
/* 168:296 */     super.setEliminado(true);
/* 169:    */   }
/* 170:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.RangoCostoEstandarProducto
 * JD-Core Version:    0.7.0.1
 */