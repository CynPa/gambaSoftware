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
/*  14:    */ import javax.persistence.Table;
/*  15:    */ import javax.persistence.TableGenerator;
/*  16:    */ import javax.validation.constraints.Min;
/*  17:    */ import javax.validation.constraints.NotNull;
/*  18:    */ import javax.validation.constraints.Size;
/*  19:    */ import org.hibernate.annotations.ColumnDefault;
/*  20:    */ 
/*  21:    */ @Entity
/*  22:    */ @Table(name="detalle_reporteador", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_reporteador", "id_detalle_reporteador_padre", "nombre"})})
/*  23:    */ public class DetalleReporteador
/*  24:    */   extends EntidadBase
/*  25:    */ {
/*  26:    */   private static final long serialVersionUID = 1L;
/*  27:    */   @Id
/*  28:    */   @TableGenerator(name="detalle_reporteador", initialValue=0, allocationSize=50)
/*  29:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="detalle_reporteador")
/*  30:    */   @Column(name="id_detalle_reporteador")
/*  31:    */   private int idDetalleReporteador;
/*  32:    */   @Column(name="id_organizacion", nullable=false)
/*  33:    */   private int idOrganizacion;
/*  34:    */   @Column(name="id_sucursal", nullable=false)
/*  35:    */   private int idSucursal;
/*  36:    */   @Column(name="nombre", nullable=false)
/*  37:    */   @Size(min=2, max=50)
/*  38:    */   @NotNull
/*  39:    */   private String nombre;
/*  40:    */   @Column(name="expresion", nullable=true)
/*  41:    */   @Size(max=500)
/*  42:    */   private String expresion;
/*  43:    */   @Column(name="descripcion", nullable=true)
/*  44:    */   @Size(max=500)
/*  45:    */   private String descripcion;
/*  46:    */   @Column(name="orden", nullable=false)
/*  47:    */   @Min(0L)
/*  48:    */   @NotNull
/*  49:    */   private int orden;
/*  50:    */   @Column(name="nivel", nullable=false)
/*  51:    */   @Min(0L)
/*  52:    */   @NotNull
/*  53:    */   @ColumnDefault("0")
/*  54:    */   private int nivel;
/*  55:    */   @Column(name="activo", nullable=false)
/*  56:    */   @NotNull
/*  57: 85 */   private boolean activo = true;
/*  58:    */   @Column(name="indicador_hoja", nullable=false)
/*  59:    */   @NotNull
/*  60:    */   @ColumnDefault("'0'")
/*  61:    */   private boolean indicadorHoja;
/*  62:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  63:    */   @JoinColumn(name="id_reporteador", nullable=false)
/*  64:    */   @NotNull
/*  65:    */   private Reporteador reporteador;
/*  66:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  67:    */   @JoinColumn(name="id_detalle_reporteador_padre", nullable=true)
/*  68:    */   private DetalleReporteador detalleReporteadorPadre;
/*  69:    */   @OneToMany(mappedBy="detalleReporteadorPadre", fetch=FetchType.LAZY)
/*  70:104 */   private List<DetalleReporteador> listaDetalleReporteadorHijo = new ArrayList();
/*  71:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  72:    */   @JoinColumn(name="id_detalle_reporteador_variable", nullable=true)
/*  73:    */   private DetalleReporteadorVariable detalleReporteadorVariable;
/*  74:    */   
/*  75:    */   public int getId()
/*  76:    */   {
/*  77:113 */     return this.idDetalleReporteador;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public int getIdDetalleReporteador()
/*  81:    */   {
/*  82:117 */     return this.idDetalleReporteador;
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void setIdDetalleReporteador(int idDetalleReporteador)
/*  86:    */   {
/*  87:121 */     this.idDetalleReporteador = idDetalleReporteador;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public int getIdOrganizacion()
/*  91:    */   {
/*  92:125 */     return this.idOrganizacion;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void setIdOrganizacion(int idOrganizacion)
/*  96:    */   {
/*  97:129 */     this.idOrganizacion = idOrganizacion;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public int getIdSucursal()
/* 101:    */   {
/* 102:133 */     return this.idSucursal;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void setIdSucursal(int idSucursal)
/* 106:    */   {
/* 107:137 */     this.idSucursal = idSucursal;
/* 108:    */   }
/* 109:    */   
/* 110:    */   public String getNombre()
/* 111:    */   {
/* 112:141 */     return this.nombre;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void setNombre(String nombre)
/* 116:    */   {
/* 117:145 */     this.nombre = nombre;
/* 118:    */   }
/* 119:    */   
/* 120:    */   public int getOrden()
/* 121:    */   {
/* 122:149 */     return this.orden;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void setOrden(int orden)
/* 126:    */   {
/* 127:153 */     this.orden = orden;
/* 128:    */   }
/* 129:    */   
/* 130:    */   public boolean isActivo()
/* 131:    */   {
/* 132:157 */     return this.activo;
/* 133:    */   }
/* 134:    */   
/* 135:    */   public void setActivo(boolean activo)
/* 136:    */   {
/* 137:161 */     this.activo = activo;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public Reporteador getReporteador()
/* 141:    */   {
/* 142:165 */     return this.reporteador;
/* 143:    */   }
/* 144:    */   
/* 145:    */   public void setReporteador(Reporteador reporteador)
/* 146:    */   {
/* 147:169 */     this.reporteador = reporteador;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public DetalleReporteador getDetalleReporteadorPadre()
/* 151:    */   {
/* 152:173 */     return this.detalleReporteadorPadre;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public void setDetalleReporteadorPadre(DetalleReporteador detalleReporteadorPadre)
/* 156:    */   {
/* 157:177 */     this.detalleReporteadorPadre = detalleReporteadorPadre;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public List<DetalleReporteador> getListaDetalleReporteadorHijo()
/* 161:    */   {
/* 162:181 */     return this.listaDetalleReporteadorHijo;
/* 163:    */   }
/* 164:    */   
/* 165:    */   public void setListaDetalleReporteadorHijo(List<DetalleReporteador> listaDetalleReporteadorHijo)
/* 166:    */   {
/* 167:185 */     this.listaDetalleReporteadorHijo = listaDetalleReporteadorHijo;
/* 168:    */   }
/* 169:    */   
/* 170:    */   public String getDescripcion()
/* 171:    */   {
/* 172:189 */     return this.descripcion;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public void setDescripcion(String descripcion)
/* 176:    */   {
/* 177:193 */     this.descripcion = descripcion;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public int getNivel()
/* 181:    */   {
/* 182:197 */     return this.nivel;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public void setNivel(int nivel)
/* 186:    */   {
/* 187:201 */     this.nivel = nivel;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public boolean isIndicadorHoja()
/* 191:    */   {
/* 192:205 */     return this.indicadorHoja;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public void setIndicadorHoja(boolean indicadorHoja)
/* 196:    */   {
/* 197:209 */     this.indicadorHoja = indicadorHoja;
/* 198:    */   }
/* 199:    */   
/* 200:    */   public String getExpresion()
/* 201:    */   {
/* 202:213 */     return this.expresion;
/* 203:    */   }
/* 204:    */   
/* 205:    */   public void setExpresion(String expresion)
/* 206:    */   {
/* 207:217 */     this.expresion = expresion;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public DetalleReporteadorVariable getDetalleReporteadorVariable()
/* 211:    */   {
/* 212:221 */     return this.detalleReporteadorVariable;
/* 213:    */   }
/* 214:    */   
/* 215:    */   public void setDetalleReporteadorVariable(DetalleReporteadorVariable detalleReporteadorVariable)
/* 216:    */   {
/* 217:225 */     this.detalleReporteadorVariable = detalleReporteadorVariable;
/* 218:    */   }
/* 219:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.DetalleReporteador
 * JD-Core Version:    0.7.0.1
 */