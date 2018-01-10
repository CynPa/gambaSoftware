/*   1:    */ package com.asinfo.as2.entities.presupuesto;
/*   2:    */ 
/*   3:    */ import com.asinfo.as2.entities.CuentaContable;
/*   4:    */ import com.asinfo.as2.entities.EntidadBase;
/*   5:    */ import com.asinfo.as2.enumeraciones.GrupoCuenta;
/*   6:    */ import java.io.Serializable;
/*   7:    */ import java.util.ArrayList;
/*   8:    */ import java.util.List;
/*   9:    */ import javax.persistence.Column;
/*  10:    */ import javax.persistence.Entity;
/*  11:    */ import javax.persistence.EnumType;
/*  12:    */ import javax.persistence.Enumerated;
/*  13:    */ import javax.persistence.FetchType;
/*  14:    */ import javax.persistence.GeneratedValue;
/*  15:    */ import javax.persistence.GenerationType;
/*  16:    */ import javax.persistence.Id;
/*  17:    */ import javax.persistence.JoinColumn;
/*  18:    */ import javax.persistence.ManyToOne;
/*  19:    */ import javax.persistence.OneToMany;
/*  20:    */ import javax.persistence.Table;
/*  21:    */ import javax.persistence.TableGenerator;
/*  22:    */ import javax.persistence.Transient;
/*  23:    */ import javax.validation.constraints.NotNull;
/*  24:    */ import javax.validation.constraints.Size;
/*  25:    */ 
/*  26:    */ @Entity
/*  27:    */ @Table(name="partida_presupuestaria", uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"id_organizacion", "codigo"})})
/*  28:    */ public class PartidaPresupuestaria
/*  29:    */   extends EntidadBase
/*  30:    */   implements Serializable
/*  31:    */ {
/*  32:    */   private static final long serialVersionUID = -1491919577671475147L;
/*  33:    */   @Id
/*  34:    */   @TableGenerator(name="partida_presupuestaria", initialValue=0, allocationSize=50)
/*  35:    */   @GeneratedValue(strategy=GenerationType.TABLE, generator="partida_presupuestaria")
/*  36:    */   @Column(name="id_partida_presupuestaria")
/*  37:    */   private int idPartidaPresupuestaria;
/*  38:    */   @Column(name="id_organizacion", nullable=false)
/*  39:    */   private int idOrganizacion;
/*  40:    */   @Column(name="id_sucursal", nullable=false)
/*  41:    */   private int idSucursal;
/*  42:    */   @Column(name="codigo", nullable=false, length=20)
/*  43:    */   @NotNull
/*  44:    */   @Size(min=1, max=20)
/*  45:    */   private String codigo;
/*  46:    */   @Column(name="nombre", nullable=false, length=100)
/*  47:    */   @NotNull
/*  48:    */   @Size(min=2, max=100)
/*  49:    */   private String nombre;
/*  50:    */   @Column(name="descripcion", length=200, nullable=true)
/*  51:    */   @Size(max=200)
/*  52:    */   private String descripcion;
/*  53:    */   @Column(name="indicador_movimiento", nullable=false)
/*  54:    */   private boolean indicadorMovimiento;
/*  55:    */   @Column(name="predeterminado", nullable=false)
/*  56:    */   private boolean predeterminado;
/*  57:    */   @Column(name="activo", nullable=false)
/*  58:    */   private boolean activo;
/*  59:    */   @Enumerated(EnumType.ORDINAL)
/*  60:    */   @Column(name="grupo_partida_presupuestaria", nullable=false)
/*  61:    */   private GrupoCuenta grupoPartidaPresupuestaria;
/*  62:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  63:    */   @JoinColumn(name="id_nivel_partida_presupuestaria", nullable=false)
/*  64:    */   @NotNull
/*  65:    */   private NivelPartidaPresupuestaria nivelPartidaPresupuestaria;
/*  66:    */   @ManyToOne(fetch=FetchType.LAZY)
/*  67:    */   @JoinColumn(name="id_partida_presupuestaria_padre", nullable=true)
/*  68:    */   private PartidaPresupuestaria partidaPresupuestariaPadre;
/*  69:    */   @OneToMany(fetch=FetchType.LAZY, mappedBy="partidaPresupuestaria")
/*  70:109 */   private List<CuentaContable> listaCuentaContable = new ArrayList();
/*  71:    */   @Transient
/*  72:    */   private String traNombreParaMostrar;
/*  73:    */   @Transient
/*  74:    */   private String mascara;
/*  75:    */   
/*  76:    */   public int getIdPartidaPresupuestaria()
/*  77:    */   {
/*  78:138 */     return this.idPartidaPresupuestaria;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void setIdPartidaPresupuestaria(int idPartidaPresupuestaria)
/*  82:    */   {
/*  83:148 */     this.idPartidaPresupuestaria = idPartidaPresupuestaria;
/*  84:    */   }
/*  85:    */   
/*  86:    */   public int getIdOrganizacion()
/*  87:    */   {
/*  88:157 */     return this.idOrganizacion;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void setIdOrganizacion(int idOrganizacion)
/*  92:    */   {
/*  93:167 */     this.idOrganizacion = idOrganizacion;
/*  94:    */   }
/*  95:    */   
/*  96:    */   public int getIdSucursal()
/*  97:    */   {
/*  98:176 */     return this.idSucursal;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void setIdSucursal(int idSucursal)
/* 102:    */   {
/* 103:186 */     this.idSucursal = idSucursal;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public String getCodigo()
/* 107:    */   {
/* 108:195 */     return this.codigo;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public void setCodigo(String codigo)
/* 112:    */   {
/* 113:205 */     this.codigo = codigo;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public String getNombre()
/* 117:    */   {
/* 118:214 */     return this.nombre;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public void setNombre(String nombre)
/* 122:    */   {
/* 123:224 */     this.nombre = nombre;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public String getDescripcion()
/* 127:    */   {
/* 128:233 */     return this.descripcion;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public void setDescripcion(String descripcion)
/* 132:    */   {
/* 133:243 */     this.descripcion = descripcion;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public boolean isIndicadorMovimiento()
/* 137:    */   {
/* 138:252 */     return this.indicadorMovimiento;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public void setIndicadorMovimiento(boolean indicadorMovimiento)
/* 142:    */   {
/* 143:262 */     this.indicadorMovimiento = indicadorMovimiento;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public boolean isPredeterminado()
/* 147:    */   {
/* 148:271 */     return this.predeterminado;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public void setPredeterminado(boolean predeterminado)
/* 152:    */   {
/* 153:281 */     this.predeterminado = predeterminado;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public boolean isActivo()
/* 157:    */   {
/* 158:290 */     return this.activo;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public void setActivo(boolean activo)
/* 162:    */   {
/* 163:300 */     this.activo = activo;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public GrupoCuenta getGrupoPartidaPresupuestaria()
/* 167:    */   {
/* 168:309 */     return this.grupoPartidaPresupuestaria;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public void setGrupoPartidaPresupuestaria(GrupoCuenta grupoPartidaPresupuestaria)
/* 172:    */   {
/* 173:319 */     this.grupoPartidaPresupuestaria = grupoPartidaPresupuestaria;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public NivelPartidaPresupuestaria getNivelPartidaPresupuestaria()
/* 177:    */   {
/* 178:328 */     return this.nivelPartidaPresupuestaria;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public void setNivelPartidaPresupuestaria(NivelPartidaPresupuestaria nivelPartidaPresupuestaria)
/* 182:    */   {
/* 183:338 */     this.nivelPartidaPresupuestaria = nivelPartidaPresupuestaria;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public PartidaPresupuestaria getPartidaPresupuestariaPadre()
/* 187:    */   {
/* 188:347 */     return this.partidaPresupuestariaPadre;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public void setPartidaPresupuestariaPadre(PartidaPresupuestaria partidaPresupuestariaPadre)
/* 192:    */   {
/* 193:357 */     this.partidaPresupuestariaPadre = partidaPresupuestariaPadre;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public int getId()
/* 197:    */   {
/* 198:367 */     return this.idPartidaPresupuestaria;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public String getTraNombreParaMostrar()
/* 202:    */   {
/* 203:371 */     this.traNombreParaMostrar = (getCodigo() + "\t|\t" + getNombre());
/* 204:372 */     return this.traNombreParaMostrar;
/* 205:    */   }
/* 206:    */   
/* 207:    */   public String getMascara()
/* 208:    */   {
/* 209:381 */     return this.mascara;
/* 210:    */   }
/* 211:    */   
/* 212:    */   public void setMascara(String mascara)
/* 213:    */   {
/* 214:391 */     this.mascara = mascara;
/* 215:    */   }
/* 216:    */   
/* 217:    */   public void setTraNombreParaMostrar(String traNombreParaMostrar)
/* 218:    */   {
/* 219:401 */     this.traNombreParaMostrar = traNombreParaMostrar;
/* 220:    */   }
/* 221:    */   
/* 222:    */   public List<CuentaContable> getListaCuentaContable()
/* 223:    */   {
/* 224:410 */     return this.listaCuentaContable;
/* 225:    */   }
/* 226:    */   
/* 227:    */   public void setListaCuentaContable(List<CuentaContable> listaCuentaContable)
/* 228:    */   {
/* 229:420 */     this.listaCuentaContable = listaCuentaContable;
/* 230:    */   }
/* 231:    */ }


/* Location:           C:\backups\AS2(26-10-2017)\WEB-INF\classes\
 * Qualified Name:     com.asinfo.as2.entities.presupuesto.PartidaPresupuestaria
 * JD-Core Version:    0.7.0.1
 */