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
/*  11:    */ import javax.persistence.JoinColumn;
/*  12:    */ import javax.persistence.ManyToOne;
/*  13:    */ import javax.persistence.OneToMany;
/*  14:    */ import javax.persistence.OrderBy;
/*  15:    */ import javax.persistence.Table;
/*  16:    */ import javax.persistence.TableGenerator;
/*  17:    */ import javax.validation.constraints.NotNull;
/*  18:    */ import javax.validation.constraints.Size;
/*  19:    */ import org.hibernate.annotations.ColumnDefault;
/*  20:    */ 
/*  21:    */ @Entity
/*  22:    */ @Table(name="punto_de_venta", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "id_sucursal", "codigo"})})
/*  23:    */ public class PuntoDeVenta
/*  24:    */   extends EntidadBase
/*  25:    */ {
/*  26:    */   private static final long serialVersionUID = 1L;
/*  27:    */   @Id
/*  28:    */   @TableGenerator(name="punto_de_venta", initialValue=0, allocationSize=50)
/*  29:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="punto_de_venta")
/*  30:    */   @Column(name="id_punto_de_venta", unique=true, nullable=false)
/*  31:    */   private int idPuntoDeVenta;
/*  32:    */   @Column(name="id_organizacion", nullable=false)
/*  33:    */   private int idOrganizacion;
/*  34:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  35:    */   @JoinColumn(name="id_sucursal", nullable=false)
/*  36:    */   private Sucursal sucursal;
/*  37:    */   @Column(name="codigo", nullable=false, length=3)
/*  38:    */   @NotNull
/*  39:    */   @Size(min=3, max=3)
/*  40:    */   private String codigo;
/*  41:    */   @Column(name="codigo_alterno", length=10, nullable=false)
/*  42:    */   @NotNull
/*  43:    */   @Size(max=10)
/*  44: 53 */   private String codigoAlterno = "";
/*  45:    */   @Column(name="nombre", nullable=false, length=50)
/*  46:    */   @NotNull
/*  47:    */   @Size(min=2, max=50)
/*  48:    */   private String nombre;
/*  49:    */   @Column(name="descripcion", nullable=true, length=200)
/*  50:    */   @Size(max=200)
/*  51:    */   private String descripcion;
/*  52:    */   @Column(name="activo", nullable=false)
/*  53:    */   private boolean activo;
/*  54:    */   @Column(name="predeterminado", nullable=false)
/*  55:    */   private boolean predeterminado;
/*  56:    */   @Column(name="indicador_pos", nullable=false)
/*  57:    */   @ColumnDefault("'0'")
/*  58:    */   private boolean indicadorPos;
/*  59:    */   @OrderBy("codigo")
/*  60:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="puntoDeVenta")
/*  61: 77 */   private List<Caja> listaCaja = new ArrayList();
/*  62:    */   
/*  63:    */   public int getId()
/*  64:    */   {
/*  65: 83 */     return this.idPuntoDeVenta;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public int getIdPuntoDeVenta()
/*  69:    */   {
/*  70: 95 */     return this.idPuntoDeVenta;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void setIdPuntoDeVenta(int idPuntoDeVenta)
/*  74:    */   {
/*  75:105 */     this.idPuntoDeVenta = idPuntoDeVenta;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public int getIdOrganizacion()
/*  79:    */   {
/*  80:114 */     return this.idOrganizacion;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void setIdOrganizacion(int idOrganizacion)
/*  84:    */   {
/*  85:124 */     this.idOrganizacion = idOrganizacion;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public Sucursal getSucursal()
/*  89:    */   {
/*  90:133 */     return this.sucursal;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public void setSucursal(Sucursal sucursal)
/*  94:    */   {
/*  95:143 */     this.sucursal = sucursal;
/*  96:    */   }
/*  97:    */   
/*  98:    */   public String getCodigo()
/*  99:    */   {
/* 100:152 */     return this.codigo;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public void setCodigo(String codigo)
/* 104:    */   {
/* 105:162 */     this.codigo = codigo;
/* 106:    */   }
/* 107:    */   
/* 108:    */   public String getDescripcion()
/* 109:    */   {
/* 110:171 */     return this.descripcion;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void setDescripcion(String descripcion)
/* 114:    */   {
/* 115:181 */     this.descripcion = descripcion;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public String getNombre()
/* 119:    */   {
/* 120:190 */     return this.nombre;
/* 121:    */   }
/* 122:    */   
/* 123:    */   public void setNombre(String nombre)
/* 124:    */   {
/* 125:200 */     this.nombre = nombre;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public boolean isActivo()
/* 129:    */   {
/* 130:209 */     return this.activo;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void setActivo(boolean activo)
/* 134:    */   {
/* 135:219 */     this.activo = activo;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public boolean isPredeterminado()
/* 139:    */   {
/* 140:228 */     return this.predeterminado;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public void setPredeterminado(boolean predeterminado)
/* 144:    */   {
/* 145:238 */     this.predeterminado = predeterminado;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public List<Caja> getListaCaja()
/* 149:    */   {
/* 150:247 */     return this.listaCaja;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public void setListaCaja(List<Caja> listaCaja)
/* 154:    */   {
/* 155:257 */     this.listaCaja = listaCaja;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public String toString()
/* 159:    */   {
/* 160:262 */     return this.nombre;
/* 161:    */   }
/* 162:    */   
/* 163:    */   public String getCodigoAlterno()
/* 164:    */   {
/* 165:266 */     return this.codigoAlterno;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public void setCodigoAlterno(String codigoAlterno)
/* 169:    */   {
/* 170:270 */     this.codigoAlterno = codigoAlterno;
/* 171:    */   }
/* 172:    */   
/* 173:    */   public boolean isIndicadorPos()
/* 174:    */   {
/* 175:274 */     return this.indicadorPos;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public void setIndicadorPos(boolean indicadorPos)
/* 179:    */   {
/* 180:278 */     this.indicadorPos = indicadorPos;
/* 181:    */   }
/* 182:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.PuntoDeVenta
 * JD-Core Version:    0.7.0.1
 */