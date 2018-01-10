/*   1:    */ package com.asinfo.as2.entities;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.math.BigDecimal;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.List;
/*   7:    */ import javax.persistence.Column;
/*   8:    */ import javax.persistence.Entity;
/*   9:    */ import javax.persistence.FetchType;
/*  10:    */ import javax.persistence.GeneratedValue;
/*  11:    */ import javax.persistence.GenerationType;
/*  12:    */ import javax.persistence.Id;
/*  13:    */ import javax.persistence.JoinColumn;
/*  14:    */ import javax.persistence.ManyToOne;
/*  15:    */ import javax.persistence.Table;
/*  16:    */ import javax.persistence.TableGenerator;
/*  17:    */ import javax.validation.constraints.NotNull;
/*  18:    */ import javax.validation.constraints.Size;
/*  19:    */ 
/*  20:    */ @Entity
/*  21:    */ @Table(name="presentacion_producto", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "nombre"})})
/*  22:    */ public class PresentacionProducto
/*  23:    */   extends EntidadBase
/*  24:    */   implements Serializable
/*  25:    */ {
/*  26:    */   private static final long serialVersionUID = 1L;
/*  27:    */   @Id
/*  28:    */   @TableGenerator(name="presentacion_producto", initialValue=0, allocationSize=50)
/*  29:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="presentacion_producto")
/*  30:    */   @Column(name="id_presentacion_producto")
/*  31:    */   private int idPresentacionProducto;
/*  32:    */   @Column(name="id_organizacion", nullable=false)
/*  33:    */   private int idOrganizacion;
/*  34:    */   @Column(name="id_sucursal")
/*  35:    */   private int idSucursal;
/*  36:    */   @Column(name="nombre", length=50, nullable=false)
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
/*  47:    */   @Column(name="cantidad_unidades", nullable=true, precision=12, scale=2)
/*  48: 75 */   private BigDecimal cantidadUnidades = BigDecimal.ONE;
/*  49:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  50:    */   @JoinColumn(name="id_tipo_presentacion_producto", nullable=true)
/*  51:    */   private TipoPresentacionProducto tipoPresentacionProducto;
/*  52:    */   
/*  53:    */   public int getId()
/*  54:    */   {
/*  55: 89 */     return this.idPresentacionProducto;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public PresentacionProducto() {}
/*  59:    */   
/*  60:    */   public PresentacionProducto(int idPresentacionProducto)
/*  61:    */   {
/*  62:100 */     this.idPresentacionProducto = idPresentacionProducto;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public PresentacionProducto(int idPresentacionProducto, String nombre)
/*  66:    */   {
/*  67:112 */     this.idPresentacionProducto = idPresentacionProducto;
/*  68:113 */     this.nombre = nombre;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public int getIdPresentacionProducto()
/*  72:    */   {
/*  73:122 */     return this.idPresentacionProducto;
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void setIdPresentacionProducto(int idPresentacionProducto)
/*  77:    */   {
/*  78:132 */     this.idPresentacionProducto = idPresentacionProducto;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public String getNombre()
/*  82:    */   {
/*  83:141 */     return this.nombre;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public void setNombre(String nombre)
/*  87:    */   {
/*  88:151 */     this.nombre = nombre;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public String getDescripcion()
/*  92:    */   {
/*  93:160 */     return this.descripcion;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void setDescripcion(String descripcion)
/*  97:    */   {
/*  98:170 */     this.descripcion = descripcion;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public boolean isActivo()
/* 102:    */   {
/* 103:179 */     return this.activo;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public void setActivo(boolean activo)
/* 107:    */   {
/* 108:189 */     this.activo = activo;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public boolean isPredeterminado()
/* 112:    */   {
/* 113:198 */     return this.predeterminado;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public void setPredeterminado(boolean predeterminado)
/* 117:    */   {
/* 118:208 */     this.predeterminado = predeterminado;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public int getIdOrganizacion()
/* 122:    */   {
/* 123:217 */     return this.idOrganizacion;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public void setIdOrganizacion(int idOrganizacion)
/* 127:    */   {
/* 128:227 */     this.idOrganizacion = idOrganizacion;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public int getIdSucursal()
/* 132:    */   {
/* 133:231 */     return this.idSucursal;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public void setIdSucursal(int idSucursal)
/* 137:    */   {
/* 138:235 */     this.idSucursal = idSucursal;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public String toString()
/* 142:    */   {
/* 143:245 */     return this.nombre;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public List<String> getCamposAuditables()
/* 147:    */   {
/* 148:249 */     ArrayList<String> lista = new ArrayList();
/* 149:250 */     lista.add("codigo");
/* 150:251 */     lista.add("nombre");
/* 151:252 */     lista.add("descripcion");
/* 152:253 */     lista.add("activo");
/* 153:    */     
/* 154:255 */     return lista;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public BigDecimal getCantidadUnidades()
/* 158:    */   {
/* 159:259 */     if (this.cantidadUnidades == null) {
/* 160:260 */       this.cantidadUnidades = BigDecimal.ONE;
/* 161:    */     }
/* 162:262 */     return this.cantidadUnidades;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public void setCantidadUnidades(BigDecimal cantidadUnidades)
/* 166:    */   {
/* 167:266 */     this.cantidadUnidades = cantidadUnidades;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public TipoPresentacionProducto getTipoPresentacionProducto()
/* 171:    */   {
/* 172:270 */     return this.tipoPresentacionProducto;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public void setTipoPresentacionProducto(TipoPresentacionProducto tipoPresentacionProducto)
/* 176:    */   {
/* 177:274 */     this.tipoPresentacionProducto = tipoPresentacionProducto;
/* 178:    */   }
/* 179:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.PresentacionProducto
 * JD-Core Version:    0.7.0.1
 */